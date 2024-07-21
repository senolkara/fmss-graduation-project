"use client"
import React from 'react'
import { useEffect } from 'react'
import { useRouter } from 'next/navigation'


export default function Home() {
  const Router = useRouter();
  
  useEffect(() => {
    const userData = localStorage.getItem('user');
    if (!userData){
      Router.push('/auth/login');
    }
    else{
      Router.push('/product/add-product');
    }
  }, []);

  return (
    <></>
  )
}
