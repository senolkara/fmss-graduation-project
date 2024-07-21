"use client"
import { get_all } from '@/Services/cpackage'
import CPackagesDataTable from '@/components/CPackagesDataTable'
import { setCPackageData } from '@/utils/CPackageSlice'
import Cookies from 'js-cookie'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import React, { useEffect } from 'react'
import { GrDeliver } from 'react-icons/gr'
import { ToastContainer, toast } from 'react-toastify'
import { useDispatch } from 'react-redux'



export default function Page() {
  const Router = useRouter();

  useEffect(() => {
    if (!Cookies.get('token')) {
      Router.push('/dashboard')
    }
  }, [Router])

  return (
    <div className='w-full bg-gray-50 h-screen px-2 py-2'>
      <div className="text-sm breadcrumbs  border-b-2 border-b-orange-600">
        <ul className='dark:text-black'>
          <li>
            <Link href={'/dashboard'}>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="w-4 h-4 mr-2 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z"></path></svg>
              Home
            </Link>
          </li>
          <li>
            <GrDeliver className="w-4 h-4 mr-2 stroke-current" />
            Packages
          </li>
        </ul>
      </div>
      <div className='w-full h-5/6 py-2'>
        <CPackagesDataTable />
      </div>
      <ToastContainer />
    </div>
  )
}
