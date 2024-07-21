import React from 'react'
import { RxDashboard } from 'react-icons/rx'
import { AiFillHome } from 'react-icons/ai'
import { BiCategory } from 'react-icons/bi'
import { GiNewspaper } from 'react-icons/gi'
import { setNavActive } from '@/utils/UserNavSlice'
import { useDispatch } from 'react-redux'


export default function UserSidebar() {
    const dispatch =  useDispatch();
    return (
        <div className='w-60 hidden dark:text-black md:block bg-white h-full'>
            <div className='w-full text-center py-2 px-2 h-20'>
                <h1 className='flex text-2xl font-semibold items-center justify-center'><RxDashboard className='mx-2' /> Dashboard</h1>
            </div>
            <div className='w-full '>
                <ul className='flex px-4 flex-col items-start justify-center'>
                    <li onClick={() => dispatch(setNavActive('Base'))} className='py-3 px-1 mb-3'><button className='flex items-center justify-center'> <AiFillHome className='mx-2' /> Home</button></li>
                    <li onClick={() => dispatch(setNavActive('allPackages'))} className='py-3 px-1 mb-3'><button className='flex items-center justify-center'> <BiCategory className='mx-2' />Paket Satın Al</button></li>
                    <li onClick={() => dispatch(setNavActive('allOrders'))} className='py-3 px-1 mb-3'><button className='flex items-center justify-center'> <BiCategory className='mx-2' />Siparişlerim</button></li>
                    <li onClick={() => dispatch(setNavActive('allCustomerPackages'))} className='py-3 px-1 mb-3'><button className='flex items-center justify-center'> <GiNewspaper className='mx-2' />Paketlerim</button></li>
                    <li onClick={() => dispatch(setNavActive('allAdvertisements'))} className='py-3 px-1 mb-3'><button className='flex items-center justify-center'> <BiCategory className='mx-2' />İlanlarım</button></li>
                </ul>
            </div>

        </div>
    )
}
