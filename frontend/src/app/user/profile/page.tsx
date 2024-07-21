"use client"

import Cookies from 'js-cookie'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import React, { useEffect, useState, FormEvent } from 'react'
import { toast, ToastContainer } from 'react-toastify'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '@/Store/store'
import { setNavActive } from '@/utils/UserNavSlice'
import { update_user_profile } from '@/Services/user'
import { setUserData } from '@/utils/UserDataSlice'
import { setAddressData } from '@/utils/AddressDataSlice'

interface pageParam {
    cpackageId: string
}

interface userData {
    email: String,
    roleType: String,
    id: String,
    name: String,
    surname: String,
    phoneNumber: string
}

export default function Page({ params, searchParams }: { params: pageParam, searchParams: any }) {


    const [birthDate, setBirthDate] = useState(new Date());
    const Router = useRouter();
    const dispatch = useDispatch();
    const user = useSelector((state: RootState) => state.UserData.userData) as userData | null;
    const addresses = useSelector((state: RootState) => state.Address.addressData);


    useEffect(() => {
        dispatch(setNavActive('Base'))
    }, [dispatch])
  
    useEffect(() => {
        if (!Cookies.get('token')) {
            Router.push('/auth/login');
        }
        dispatch(setUserData(JSON.parse(localStorage.getItem('user') || '{}')));
        dispatch(setAddressData(JSON.parse(localStorage.getItem('addresses') || '{}')));
    }, [Router]);
  
  
    const [formData, setFormData] = useState({ 
        email: "" , 
        name : "", 
        surname : "", 
        phoneNumber: "", 
        birthDate: birthDate,
        id: "",
        title: "",
        province: "",
        district: "",
        neighbourhood: "",
        street: "",
        description: ""
    });
    const [error, setError] = useState({ email: "", name: '', surname : "" });
  
    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
  
      event.preventDefault();
      if (!formData.email) {
        setError({ ...error, email: "Email Field is Required" })
        return;
      }
      if (!formData.name) {
        setError({ ...error, name: "Name Field is required" })
        return;
      }
      if (!formData.surname) {
        setError({ ...error, surname: "Surname Field is required" })
        return;
      }

        const finalData = {
            name: formData?.name,
            surname: formData?.surname,
            email: formData?.email,
            phoneNumber: formData?.phoneNumber,
            birthDate: formData?.birthDate,
            addressRequestDto: [{
                title: formData?.title,
                province: formData?.province,
                district: formData?.district,
                neighbourhood: formData?.neighbourhood,
                street: formData?.street,
                description: formData?.description,
                userId: user?.id
            }]
        };
  
        const data = await update_user_profile(user?.id, finalData);
        console.log(data);
        if (!data.success){
            toast.error(data.message);
            return;
        }
        toast.success(data.message);
        setTimeout(() => {
            location.reload();
        }, 1000);
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
                        Profile
                    </li>
                </ul>
            </div>
            <div className='w-full h-20 my-2 text-center'>
                <h1 className='text-2xl py-2 dark:text-black'>Your Profile</h1>
            </div>


            <div className='w-full  h-full flex-col md:flex-row flex items-start justify-center'>

                <form onSubmit={handleSubmit} className="w-full max-w-lg  py-2 flex-col ">
                    <div className="form-control w-full mb-2">
                        <label htmlFor="name" className="block mb-2 text-sm font-medium text-gray-900 ">Name</label>
                        <input value={user?.name} onChange={(e) => setFormData({ ...formData, name: e.target.value })} type="text" name="name" id="namw" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="Name" />
                        {
                        error.name && <p className="text-sm text-red-500">{error.name}</p>
                        }
                    </div >
                    <div className="form-control w-full mb-2">
                        <label htmlFor="surname" className="block mb-2 text-sm font-medium text-gray-900 ">Surname</label>
                        <input value={user?.surname} onChange={(e) => setFormData({ ...formData, surname: e.target.value })} type="text" name="surname" id="namwsrnm" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="Surname" />
                        {
                        error.surname && <p className="text-sm text-red-500">{error.surname}</p>
                        }
                    </div >
                    <div className="form-control w-full mb-2">
                        <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 ">Email</label>
                        <input value={user?.email} onChange={(e) => setFormData({ ...formData, email: e.target.value })} type="email" name="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="name@company.com" />
                        {
                        error.email && <p className="text-sm text-red-500">{error.email}</p>
                        }
                    </div >
                    <div className="form-control w-full mb-2">
                        <label htmlFor="phoneNumber" className="block mb-2 text-sm font-medium text-gray-900 ">Phone Number</label>
                        <input value={user?.phoneNumber} onChange={(e) => setFormData({ ...formData, phoneNumber: e.target.value })} type="text" name="phoneNumber" id="phoneNumber" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="Phone Number" />
                    </div >
                    <div className="form-control w-full mb-2">
                        <label htmlFor="birthDate" className="block mb-2 text-sm font-medium text-gray-900 ">Birth Date</label>
                        <input onChange={() => setBirthDate(birthDate)} type="date" name="birthDate" id="birthDate" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="Birth Date" />
                    </div >

                    {
                        addresses?.length == 0 ?
                        <>
                            <div className="form-control w-full mb-2">
                                <label className="label">
                                    <span className="label-text">Your Address</span>
                                </label>
                            </div>
                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Title</span>
                                </label>
                                <input type="text" name='title' id='title' className="file-input file-input-bordered w-full " />
                            </div><div className="form-control">
                                <label className="label">
                                    <span className="label-text">Province</span>
                                </label>
                                <input type="text" name='province' id='province' className="file-input file-input-bordered w-full " />
                            </div><div className="form-control">
                                <label className="label">
                                    <span className="label-text">District</span>
                                </label>
                                <input type="text" name='district' id='district' className="file-input file-input-bordered w-full " />
                            </div><div className="form-control">
                                <label className="label">
                                    <span className="label-text">Neighbourhood</span>
                                </label>
                                <input type="text" name='neighbourhood' id='neighbourhood' className="file-input file-input-bordered w-full " />
                            </div><div className="form-control">
                                <label className="label">
                                    <span className="label-text">Street</span>
                                </label>
                                <input type="text" name='street' id='street' className="file-input file-input-bordered w-full " />
                            </div><div className="form-control">
                                <label className="label">
                                    <span className="label-text">Description</span>
                                </label>
                                <textarea className="textarea textarea-bordered h-24" name='description' placeholder="Description"></textarea>
                            </div>
                        </>
                        :
                        addresses?.map((item: index) => {
                            return (
                                <>
                                <div key={item?.id} className="form-control">
                                    <label className="label">
                                        <span className="label-text">Title</span>
                                    </label>
                                    <input type="text" name='title' id='title' className="file-input file-input-bordered w-full " 
                                    value={item?.title} />
                                </div><div className="form-control">
                                    <label className="label">
                                        <span className="label-text">Province</span>
                                    </label>
                                    <input type="text" name='province' id='province' className="file-input file-input-bordered w-full " 
                                    value={item?.province}/>
                                </div><div className="form-control">
                                    <label className="label">
                                        <span className="label-text">District</span>
                                    </label>
                                    <input type="text" name='district' id='district' className="file-input file-input-bordered w-full " 
                                    value={item?.district}/>
                                </div><div className="form-control">
                                    <label className="label">
                                        <span className="label-text">Neighbourhood</span>
                                    </label>
                                    <input type="text" name='neighbourhood' id='neighbourhood' className="file-input file-input-bordered w-full " 
                                    value={item?.neighbourhood}/>
                                </div><div className="form-control">
                                    <label className="label">
                                        <span className="label-text">Street</span>
                                    </label>
                                    <input type="text" name='street' id='street' className="file-input file-input-bordered w-full " 
                                    value={item?.street}/>
                                </div><div className="form-control">
                                    <label className="label">
                                        <span className="label-text">Description</span>
                                    </label>
                                    <textarea className="textarea textarea-bordered h-24" name='description' placeholder="Description">
                                        {item?.description}
                                    </textarea>
                                </div>
                            </>
                            )
                        })
                    }

                    <button className='btn btn-block mt-3'>Save</button>

                </form >

            </div >


            <ToastContainer />
        </div>
    )
}
