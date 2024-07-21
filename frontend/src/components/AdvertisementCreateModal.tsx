"use client";

import {useSearchParams, usePathname} from "next/navigation";
import { toast, ToastContainer } from 'react-toastify'
import Link from "next/link";
import { TailSpin } from 'react-loader-spinner'
import React, { useEffect, useState, FormEvent } from 'react'
import { useForm, SubmitHandler } from "react-hook-form";
import { RootState } from '@/Store/store';
import { useSelector } from 'react-redux'
import { useRouter } from 'next/navigation';
import { create_a_new } from "@/Services/advertisement";
import { get_all } from "@/Services/building";
import { get_all_customer_packages_by_customer_id } from "@/Services/customer-package";


  
type Inputs = {
    advertisementType: string,
    price : Number
    ,buildingId: string,
    customerPackageId: string
}

interface customerData {
    id: String,
    userId: String,
    recordStatus: String,
    accountType: String,
    score: Number
}

interface buildingData {
    id: String,
    name: String
}

interface customerPackageData {
    id: String,
    packageResponseDto: {
      packageType: String
    }
  };

export default function AdvertisementCreateModal() {
    const searchParams = useSearchParams();
    const modal = searchParams.get("advertisement_create_modal");

    const Router = useRouter();
    const [loading, setLoding] = useState<Boolean>(false);
    const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null
    const [buildingData, setBuildingData] = useState<buildingData[] | []>([]);
    const [customerPackageData, setCustomerPackageData] = useState<customerPackageData[] | []>([]);
 
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

      useEffect(() => {
        fetchCustomerPackagesData();
      }, [])
      
      const fetchCustomerPackagesData = async () => {
        const customerPackageData = await get_all_customer_packages_by_customer_id(customer?.id);
        if (customerPackageData?.success) {
          setCustomerPackageData(customerPackageData?.data);
        }
      }

    const advertisementTypes = [
        {
            key: 0,
            value: "Devren Satılık"
        },
        {
            key: 1,
            value: "Satılık"
        },
        {
            key: 2,
            value: "Kiralık"
        }
    ];

    const { register, formState: { errors }, handleSubmit } = useForm<Inputs>({
        criteriaMode: "all"
    });

    const onSubmit: SubmitHandler<Inputs> = async formData => {

        const finalData = {
            advertisementType: formData?.advertisementType,
            price: formData?.price
            ,buildingRequestDto: {
                id: formData?.buildingId
            },
            customerPackageRequestDto: {
                id: formData?.customerPackageId
            }
        };
        setLoding(true);
        const res =  await create_a_new(finalData);
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
        <>
            {modal &&
                <>
                <dialog className="fixed left-0 top-0 w-full h-full bg-black bg-opacity-50 z-50 overflow-auto backdrop-blur flex justify-center items-center">
                    <div className="bg-white m-auto p-8">
                        <div className="flex flex-col items-center">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full max-w-lg  py-2 flex-col ">
                                <div className="form-control w-full mb-2">
                                    <div className="form-control w-full max-w-full">
                                        <label className="label">
                                            <span className="label-text">Yapı</span>
                                        </label>
                                        <select   {...register("buildingId", { required: true })}  className="select select-bordered">
                                            <option disabled selected>Yapı Seçiniz</option>
                                            {
                                                buildingData?.map((item) => {
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
                                            <span className="label-text">İlan Türü</span>
                                        </label>
                                        <select {...register("advertisementType", { required: true })} className="select select-bordered">
                                            <option disabled selected>İlan Türü Seçiniz</option>
                                            {advertisementTypes?.map((item) => {
                                                return (
                                                    <option key={item.key} value={item.key}>{item.value}</option>
                                                );
                                            })}
                                        </select>
                                    </div>
                                </div>
                                <div className="form-control w-full mb-2">
                                    <div className="form-control w-full max-w-full">
                                        <label className="label">
                                            <span className="label-text">Paketiniz</span>
                                        </label>
                                        <select   {...register("customerPackageId", { required: true })}  className="select select-bordered">
                                            <option disabled selected>Paketinizi Seçiniz</option>
                                            {
                                                customerPackageData?.map((item) => {
                                                    return (
                                                        <option key={item.id} value={item.id}>{item.packageResponseDto.packageType}</option>
                                                    )
                                                })
                                            }
                                        </select>
                                    </div>
                                </div >
                                <div className="form-control w-full mb-2">
                                    <label htmlFor="price" className="block mb-2 text-sm font-medium text-gray-900 ">Fiyat</label>
                                    <input {...register("price", { required: true })} type="text" name="price" id="price" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-orange-600 focus:border-orange-600 block w-full p-2.5" placeholder="Fiyat" />
                                    {errors.price && <span className='text-red-500 text-xs mt-2'>This field is required</span>}
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
                                        </button> : <button className='btn btn-block mt-3'>Kaydet</button>
                                }  
                                
                                <br />
                                <Link href={"/dashboard"}>
                                    <button className="btn btn-block mt-3">Kapat</button>
                                </Link>
                            </form>
                        </div>
                    </div>
                </dialog>
                <ToastContainer />
                </>
            }
        </>
    );
}