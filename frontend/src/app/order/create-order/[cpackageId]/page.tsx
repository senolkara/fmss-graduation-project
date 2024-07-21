"use client"

import Cookies from 'js-cookie'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import React, { useEffect, useState, FormEvent } from 'react'
import { toast, ToastContainer } from 'react-toastify'
import { useForm, SubmitHandler } from "react-hook-form";
import { TailSpin } from 'react-loader-spinner'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '@/Store/store'
import { setNavActive } from '@/utils/UserNavSlice'
import { create_a_new } from '@/Services/order'
import { get_by_id } from '@/Services/cpackage'
import { setCPackageDetailData } from '@/utils/CPackageSlice'
import { setUserData } from '@/utils/UserDataSlice'
import { setAddressData } from '@/utils/AddressDataSlice'
import { setCustomerData } from '@/utils/CustomerDataSlice'

interface pageParam {
    cpackageId: string
}

interface customerData {
    id: String,
    userId: String,
    recordStatus: String,
    accountType: String,
    score: Number
}

interface cpackageDetailData {
    id: String,
    packageType: String,
    price: Number,
    description: String
}

export default function Page({ params, searchParams }: { params: pageParam, searchParams: any }) {

    const Router = useRouter();
    const dispatch = useDispatch();
    const [loading, setLoding] = useState<Boolean>(false);
    const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null
    const cpackageDetailData = useSelector((state: RootState) => state.CPackage.cpackageDetailData) as cpackageDetailData | null;


    useEffect(() => {
        dispatch(setNavActive('Base'))
    }, [dispatch]);

    useEffect(() => {
        if (!Cookies.get('token')) {
            Router.push('/auth/login');
        }
        dispatch(setUserData(JSON.parse(localStorage.getItem('user') || '{}')));
        dispatch(setCustomerData(JSON.parse(localStorage.getItem('customer') || '{}')));
        dispatch(setAddressData(JSON.parse(localStorage.getItem('addresses') || '{}')));
    }, [Router]);

    useEffect(() => {
        fetchCPackageDetailData();
    }, [])
      
    const fetchCPackageDetailData = async () => {
        const cpackageData = await get_by_id(params?.cpackageId);
        console.log(cpackageData);
        dispatch(setCPackageDetailData(cpackageData.data));
    }
    
    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
  
        event.preventDefault();
        setLoding(true);
        const finalData = {
            packageRequestDto: {
                id: cpackageDetailData?.id
            },
            customerRequestDto: {
                id: customer?.id
            }
        };

        const res =  await create_a_new(finalData);
        console.log(res);
        if(res?.success){
            setLoding(false);
            toast.success(res?.message)
            setTimeout(() => {
                Router.push('/dashboard')
            } , 1000)
        }else{
            toast.error(res?.message)
        }
    }   

    return (
        <div className='w-full h-full bg-gray-50 px-2'>
            <div className="text-sm breadcrumbs  border-b-2 border-b-orange-600">
                <ul className='dark:text-black'>
                    <li>
                        <Link href={"/dashboard"}>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="w-4 h-4 mr-2 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z"></path></svg>
                            Home
                        </Link>
                    </li>
                    <li>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="w-4 h-4 mr-2 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z"></path></svg>
                        Your Order
                    </li>
                </ul>
            </div>

            <div className='w-full  h-full flex-col md:flex-row flex items-start justify-center'>
                <form onSubmit={handleSubmit} className="md:w-1/3 px-2 w-full max-w-lg  py-2 flex-col ">
                    <div className='w-full flex flex-col items-center py-2 overflow-auto h-96'>
                        <div className='bg-white w-10/12  rounded-xl m-2 border-b flex-col md:flex-row h-72  md:h-40 py-2 px-4 flex justify-around items-center'>
                            <h2 className='font-semibold text-lg'>{cpackageDetailData?.packageType}</h2>
                        </div>
                        <div className='bg-white w-10/12  rounded-xl m-2 border-b flex-col md:flex-row h-72  md:h-40 py-2 px-4 flex justify-around items-center'>
                            <span className="label-text">{cpackageDetailData?.description}</span>
                        </div>
                        <div className='w-full  py-2 my-2 flex justify-end '>
                            <h1 className='py-2 tracking-widest mb-2  border-b px-6 border-orange-600 text-sm  flex flex-col '>  Original Price  <span className='text-xl font-extrabold'>{cpackageDetailData?.price || 0}</span> </h1>
                            <h1 className='py-2 tracking-widest mb-2  border-b px-6 border-orange-600 text-sm  flex flex-col '>  Shipping Price  <span className='text-xl font-extrabold'>{cpackageDetailData?.price || 0}</span> </h1>
                            <h1 className='py-2 tracking-widest mb-2  border-b px-6 border-orange-600 text-sm  flex flex-col '>  tax Price  <span className='text-xl font-extrabold'>{cpackageDetailData?.price || 0}</span> </h1>
                        </div>
                        <div className='w-full  py-2 my-2 flex justify-end '>
                            <h1 className='py-2 tracking-widest mb-2  border-b px-6 border-orange-600 text-sm  flex flex-col '>  Total Order Price  <span className='text-xl font-extrabold'>{cpackageDetailData?.price || 0}</span> </h1>
                        </div>
                    </div>
                    
                    {
                        loading ? <button type="button" className="btn btn-block mt-3">
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
                            </button> : <button className='btn btn-block mt-3'>Order!</button>
                    }        
                    

                </form >

            </div >


            <ToastContainer />
        </div>
    )
}
