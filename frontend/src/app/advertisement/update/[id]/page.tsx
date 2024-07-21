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
import { get_by_id } from '@/Services/advertisement'
import { setUserData } from '@/utils/UserDataSlice'
import { setAddressData } from '@/utils/AddressDataSlice'
import { setCustomerData } from '@/utils/CustomerDataSlice'
import { setAdvertisementDetailData } from '@/utils/AdvertisementDataSlice'
import { update_a } from '@/Services/advertisement'
import { get_all } from '@/Services/building'

interface pageParam {
    id: string
}

interface customerData {
    id: String,
    userId: String,
    recordStatus: String,
    accountType: String,
    score: Number
}

interface advertisementDetailData {
    id: String,
    advertisementType: String,
    advertisementStatus: String,
    advertisementNo: String,
    startDateTime: Date,
    finishDateTime: Date,
    price: Number,
    buildingId: String,
    customerPackageId: String
}

interface buildingData {
    id: String,
    name: String
}

type Inputs = {
    advertisementType: string,
    advertisementStatus: string,
    price : Number,
    buildingId: string,
    customerPackageId: string
}

export default function Page({ params, searchParams }: { params: pageParam, searchParams: any }) {

    const Router = useRouter();
    const dispatch = useDispatch();
    const [loading, setLoding] = useState<Boolean>(false);
    const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null
    const [buildingData, setBuildingData] = useState<buildingData[] | []>([]);
    const advertisementDetailData = useSelector((state: RootState) => state.Advertisement.advertisementDetailData) as advertisementDetailData | null;

    const advertisementTypes = [
        {
            key: 0,
            value: "Devren Satılık",
            enum: "TRANSFERABLE"
        },
        {
            key: 1,
            value: "Satılık",
            enum: "PURCHASEABLE"
        },
        {
            key: 2,
            value: "Kiralık",
            enum: "RENTABLE"
        }
    ];
    const advertisementStatuses = [
        {
            key: 0,
            value: "Taslak",
            enum: "DRAFT"
        },
        {
            key: 1,
            value: "Aktif",
            enum: "ACTIVE"
        },
        {
            key: 2,
            value: "Pasif",
            enum: "PASSIVE"
        },
        {
            key: 3,
            value: "IN_REVIEW",
            enum: "IN_REVIEW"
        }
    ];

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
        fetchAdvertisementDetailData();
    }, [])
      
    const fetchAdvertisementDetailData = async () => {
        const advertisementData = await get_by_id(params?.id);
        console.log(advertisementData);
        dispatch(setAdvertisementDetailData(advertisementData.data));
    }

    useEffect(() => {
        fetchBuildingsData();
      }, [])
      
      const fetchBuildingsData = async () => {
        const buildingData = await get_all();
        console.log(buildingData);
        if (buildingData?.success) {
            setBuildingData(buildingData?.data)
        }
      }
    
    const { register, formState: { errors }, handleSubmit } = useForm<Inputs>({
        criteriaMode: "all"
    });

    const onSubmit: SubmitHandler<Inputs> = async formData => {

        const finalData = {
            advertisementType: formData?.advertisementType,
            advertisementStatus: formData?.advertisementStatus,
            buildingId: formData?.buildingId,
            price: formData?.price
        };
        setLoding(true);
        const res =  await update_a(params?.id, finalData);
        console.log(res);
        if(res?.success){
            setLoding(false);
            toast.success(res?.message)
            setTimeout(() => {
                Router.push('/dashboard')
            } , 1000)
        }else{
            setLoding(false);
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
                        Your Advertisement
                    </li>
                </ul>
            </div>

            <div className='w-full  h-full flex-col md:flex-row flex items-start justify-center'>

                <form onSubmit={handleSubmit(onSubmit)} className="w-full max-w-lg  py-2 flex-col ">
                    <div className="form-control w-full mb-2">
                        <div className="form-control w-full max-w-full">
                            <label className="label">
                                <span className="label-text">Yapı</span>
                            </label>
                            <select   {...register("buildingId", { required: true })}  className="select select-bordered">
                                <option disabled>Yapı Seçiniz</option>
                                {
                                    buildingData?.map((item) => {
                                        if (advertisementDetailData?.buildingId == item.id){
                                            return (
                                                <option key={item.id} value={item.id} selected>{item.name}</option>
                                            )
                                        }
                                        return (
                                            <option key={item.id} value={item.id}>{item.name}</option>
                                        )
                                    })
                                }
                            </select>
                        </div>
                    </div >
                    <div className="form-control w-full mb-2">
                        <div className="form-control w-full max-w-full">
                            <label className="label">
                                <span className="label-text">İlan Durumu</span>
                            </label>
                            <select   {...register("advertisementStatus", { required: true })}  className="select select-bordered">
                                <option disabled>İlan Durumu Seçiniz</option>
                                {
                                    advertisementStatuses?.map((item) => {
                                        if (advertisementDetailData?.advertisementStatus == item.enum){
                                            return (
                                                <option key={item.key} value={item.key} selected>{item.value}</option>
                                            )
                                        }
                                        return (
                                            <option key={item.key} value={item.key}>{item.value}</option>
                                        )
                                    })
                                }
                            </select>
                        </div>
                    </div >
                    <div className="form-control w-full mb-2">
                        <div className="form-control w-full max-w-full">
                            <label className="label">
                                <span className="label-text">İlan Türü</span>
                            </label>
                            <select   {...register("advertisementType", { required: true })}  className="select select-bordered">
                                <option disabled>İlan Türü Seçiniz</option>
                                {
                                    advertisementTypes?.map((item) => {
                                        if (advertisementDetailData?.advertisementType == item.enum){
                                            return (
                                                <option key={item.key} value={item.key} selected>{item.value}</option>
                                            )
                                        }
                                        return (
                                            <option key={item.key} value={item.key}>{item.value}</option>
                                        )
                                    })
                                }
                            </select>
                        </div>
                    </div >
                    <div className="form-control w-full mb-2">
                        <label htmlFor="price" className="block mb-2 text-sm font-medium text-gray-900 ">Fiyat</label>
                        <input value={advertisementDetailData?.price} {...register("price", { required: true })} type="text" name="price" id="price" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="Fiyat" />
                        {errors.price && <span className='text-red-500 text-xs mt-2'>This field is required</span>}
                    </div >

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
                            </button> : <button className='btn btn-block mt-3'>Kaydet</button>
                    }  
                    
                </form >

            </div >

            <ToastContainer />
        </div>
    )
}
