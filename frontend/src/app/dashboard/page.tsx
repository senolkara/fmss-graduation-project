"use client"
import Cookies from 'js-cookie'
import { useRouter } from 'next/navigation'
import React, { useEffect, useState } from 'react'
import UserNavbar from '@/components/UserNavbar';
import UserSidebar from '@/components/UserSidebar';
import { RootState } from '@/Store/store'
import useSWR from 'swr'
import SuperComponent from '@/components/SuperComponent';
import { ToastContainer, toast } from 'react-toastify';
import { get_all_advertisements_by_customer_id } from '@/Services/advertisement';
import { get_all_orders_by_customer_id } from '@/Services/order';
import { get_all_customer_packages_by_customer_id } from '@/Services/customer-package';
import { useDispatch, useSelector } from 'react-redux';
import { setNavActive } from '@/utils/UserNavSlice';
import { setAdvertisementData, setCustomerPackageData, setOrderData } from '@/utils/UserSlice';
import { setCustomerData } from '@/utils/CustomerDataSlice'


interface userData {
  email: String,
  role: String,
  _id: String,
  name: String
}

interface customerData {
  id: String,
  userId: String,
  recordStatus: String,
  accountType: String,
  score: Number
}


export default function Dashboard() {
  const Router = useRouter();
  const dispatch = useDispatch();
  const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null

  useEffect(() => {
    const user: userData | null = JSON.parse(localStorage.getItem('user') || '{}');
    if (!Cookies.get('token')) {
      Router.push('/auth/login')
    }
    if (user != null){
      if (user.roleType !== 'USER'){
        Router.push('/auth/login')
      }
    }
    const customer: customerData | null = JSON.parse(localStorage.getItem('customer') || '{}');
    if (!customer?.id){
        Router.push('/auth/login');
    }
    dispatch(setNavActive('Base'))
  }, [dispatch, Cookies, Router]);

  useEffect(() => {
    if (!Cookies.get('token')) {
      Router.push('/auth/login')
    }
    const user: userData | null = JSON.parse(localStorage.getItem('user') || '{}');
    if (user != null){
      if (user.roleType !== 'USER'){
        Router.push('/auth/login')
      }
    }
    dispatch(setCustomerData(JSON.parse(localStorage.getItem('customer') || '{}')));
}, [Router]);

/*
  useEffect(() => {
    fetchAdvertisementsData();
  }, [])
  
  const fetchAdvertisementsData = async () => {
    const advertisementData = await get_all_advertisements_by_customer_id(customer?.id);
    console.log(advertisementData);
    if (advertisementData?.success) {
      dispatch(setAdvertisementData(advertisementData?.data));
    }
  }

  useEffect(() => {
    fetchOrdersData();
  }, [])

const fetchOrdersData = async () => {
  const orderData = await get_all_orders_by_customer_id(customer?.id);
  if (orderData?.success) {
    setOrderData(orderData?.data);
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
*/
const { data: advertisementData } = useSWR('/gettingAllAdvertisementForUser', () => get_all_advertisements_by_customer_id(customer?.id))
const {data : customerPackageData } = useSWR('/gettingAllCustomerPackagesForUser', () => get_all_customer_packages_by_customer_id(customer?.id))
const { data: orderData } = useSWR('/gettingAllOrdersForUser', () => get_all_orders_by_customer_id(customer?.id))

useEffect(() => {
  dispatch(setAdvertisementData(advertisementData?.data))
  dispatch(setOrderData(orderData?.data))
  dispatch(setCustomerPackageData(customerPackageData?.data))
}, [advertisementData, orderData, customerPackageData, dispatch])

  return (
    <div className='w-full h-screen flex  bg-gray-50 overflow-hidden'>
      <UserSidebar />
      <div className='w-full h-full '>
        <UserNavbar />
        <div className='w-full h-5/6  flex flex-wrap items-start justify-center overflow-y-auto  px-4 py-2'>
          {/*categoryLoading ? <Loading /> : <SuperComponent />*/}
          {<SuperComponent />}
        </div>
      </div>
      <ToastContainer />
    </div>
  )
}



