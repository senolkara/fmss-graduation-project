"use client"

import Cookies from 'js-cookie'
import Image from 'next/image'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import React from 'react'
import { useDispatch } from 'react-redux'

export default function UserNavbar() {
    const router =  useRouter();

    const handleLogout = () => {
        Cookies.remove('token');
        localStorage.clear();
        if (!Cookies.get('token')){
            router.push('/auth/login');
        }
    }

    return (
        <div className="navbar dark:text-black bg-white">
            <div className="flex-1">
                <div className="dropdown md:hidden">
                    
                </div>
            </div>
            <div className="flex-none">
                <div className="dropdown dropdown-end">
                    <label tabIndex={0} className="btn btn-ghost btn-circle avatar">
                        <div className="w-10 relative rounded-full">
                            <Image className='rounded-full' fill alt='none' src="/profile.jpg" />
                        </div>
                    </label>
                    <ul tabIndex={0} className="menu menu-compact dropdown-content mt-3 p-2 shadow bg-gray-50 rounded-box w-52">
                        <li>
                            <Link href={"/user/profile"} className="justify-between">
                                Profile
                            </Link>
                        </li>
                        <li onClick={handleLogout}><button> Logout </button></li>
                    </ul>
                </div>
            </div>
        </div>
    )
}
