"use client"

import React, { useEffect, useState, FormEvent } from 'react'
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Link from 'next/link';
import { login_me } from '@/Services/auth';
import Cookies from 'js-cookie';
import { useDispatch } from 'react-redux';
import { setUserData } from '@/utils/UserDataSlice';
import { useRouter } from 'next/navigation';
import { TailSpin } from 'react-loader-spinner';
import { get_by_user_id } from '@/Services/customer';
import { setCustomerData, setCustomerUserData } from '@/utils/CustomerDataSlice';
import { get_addresses_by_user_id } from '@/Services/user';
import { setAddressData } from '@/utils/AddressDataSlice';


export default function Login() {
    const dispatch = useDispatch()
    const Router = useRouter()

    const [formData, setFormData] = useState({ email: "", password: "" });
    const [error, setError] = useState({ email: "", password: "" });
    const [loading, setLoding] = useState<Boolean>(false);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {

        event.preventDefault();
        setLoding(true);
        if (!formData.email) {
            setError({ ...error, email: "Email Field is Required" })
            setLoding(false);
            return;
        }
        if (!formData.password) {
            setError({ ...error, password: "Password Field is required" })
            setLoding(false);
            return;
        }

        const data = await login_me(formData);
        if (!data.success){
            toast.error(data.message);
            setLoding(false);
            return;
        }
        const custByUserData = await get_by_user_id(data.data.user.id);
        if (!custByUserData.success){
            toast.error(custByUserData.message);
            setLoding(false);
            return;
        }
        setLoding(false);
        const addressesByUserData = await get_addresses_by_user_id(data.data.user.id);
        console.log(addressesByUserData.data.data);
        toast.success(data.message);
        Cookies.set('token', data.data.access_token);
        localStorage.setItem('user', JSON.stringify(data.data.user));
        localStorage.setItem('customer', JSON.stringify(custByUserData.data));
        localStorage.setItem('addresses', JSON.stringify(addressesByUserData.data.data));
        const userData = localStorage.getItem('user');
        const customerData = localStorage.getItem('customer');
        const addressesData = localStorage.getItem('addresses');
        const userDataString = typeof userData === 'string' ? userData : '';
        const customerDataString = typeof customerData === 'string' ? customerData : '';
        const addressesDataString = typeof addressesData === 'string' ? addressesData : '';
        dispatch(setUserData(JSON.parse(userDataString)));
        dispatch(setCustomerData(JSON.parse(customerDataString)));
        dispatch(setCustomerUserData(JSON.parse(userDataString)));
        dispatch(setAddressData(JSON.parse(addressesDataString)));
        Router.push('/dashboard');
    }


    useEffect(() => {
        if (Cookies.get('token')) {
            Router.push('/dashboard');
        }
    }, [Router])


    return (
        <>
            <div className='w-full h-screen bg-gray-50 text-black'>
                <div className="flex flex-col items-center  text-center justify-center px-6 py-8 mx-auto h-screen lg:py-0">
                    <div className="w-full bg-white text-black rounded-lg shadow  md:mt-0 sm:max-w-md xl:p-0 ">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl ">
                                Sign in to your account
                            </h1>
                            <form onSubmit={handleSubmit} className="space-y-4 md:space-y-6" action="#">
                                <div className='text-left'>
                                    <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 ">Your email</label>
                                    <input onChange={(e) => setFormData({ ...formData, email: e.target.value })} type="email" name="email" id="email" className="bg-gray-50  border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5 " placeholder="name@company.com" />
                                    {
                                        error.email && <p className="text-sm text-red-500">{error.email}</p>
                                    }
                                </div>
                                <div className='text-left'>
                                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 ">Password</label>
                                    <input onChange={(e) => setFormData({ ...formData, password: e.target.value })} type="password" name="password" id="password" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" />
                                    {
                                        error.password && <p className="text-sm text-red-500">{error.password}</p>
                                    }
                                </div>
                                
                                {
                                    loading ? <button type="button" className="w-full flex items-center justify-center text-white bg-orange-600 hover:bg-orange-700 focus:ring-4 focus:outline-none focus:ring-orange-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-orange-600 dark:hover:bg-orange-700 dark:focus:ring-orange-800">
                                        <TailSpin
                                            height="20"
                                            width="20"
                                            color="white"
                                            ariaLabel="tail-spin-loading"
                                            radius="1"
                                            wrapperStyle={{}}
                                            wrapperClass=""
                                            visible={true}
                                        />
                                        </button> : <button type="submit" className="w-full text-white bg-orange-600 da hover:bg-orange-700 focus:ring-4 focus:outline-none focus:ring-orange-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-orange-600 dark:hover:bg-orange-700 dark:focus:ring-orange-800">Sign in</button>
                                }
                                
                                <p className="text-sm text-black ">
                                    Don’t have an account yet? <Link href={"/auth/register"} className="font-medium text-orange-600 hover:underline ">Sign up</Link>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>
                <ToastContainer />
            </div>
        </>
    )
}
