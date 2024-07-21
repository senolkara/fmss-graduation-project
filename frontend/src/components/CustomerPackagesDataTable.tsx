"use Client"

import React, { useEffect, useState } from 'react'
import DataTable from 'react-data-table-component';
import { RootState } from '@/Store/store';
import { get_all_customer_packages_by_customer_id } from '@/Services/customer-package'
import { setUserData } from '@/utils/UserDataSlice'
import { setAddressData } from '@/utils/AddressDataSlice'
import { setCustomerData } from '@/utils/CustomerDataSlice'
import { useSelector, useDispatch } from 'react-redux'
import { useRouter } from 'next/navigation';
import Cookies from 'js-cookie'


type customerPackageData = {
  id: string,
  startDateTime: Date,
  finishDateTime: Date,
  recordStatus: string,
  advertisementCount: Number,
  packageResponseDto: {
    packageType: string
  },
  customerId: string
};

interface customerData {
    id: String,
    userId: String,
    recordStatus: String,
    accountType: String,
    score: Number
}


export default function CustomerPackagesDataTable() {
  const router = useRouter();
  const dispatch = useDispatch();
  const [customerPackageData, setCustomerPackageData] = useState<customerPackageData[] | []>([]);
  const data = useSelector((state: RootState) => state.CustomerPackage.customerPackage)
  const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null

  const [search, setSearch] = useState('');
  const [filteredData, setFilteredData] = useState<customerPackageData[] | []>([]);

  useEffect(() => {
    setCustomerPackageData(data)
  }, [data])



  useEffect(() => {
    setFilteredData(customerPackageData);
  }, [ customerPackageData])


  useEffect(() => {
        if (!Cookies.get('token')) {
            router.push('/auth/login');
        }    
        dispatch(setCustomerData(JSON.parse(localStorage.getItem('customer') || '{}')));
        if (!customer?.id){
            router.push('/auth/login');
        }
        dispatch(setUserData(JSON.parse(localStorage.getItem('user') || '{}')));
        dispatch(setAddressData(JSON.parse(localStorage.getItem('addresses') || '{}')));
  }, [router]);


  const columns = [
    {
        name: 'Paket Tipi',
        selector: (row: customerPackageData) => row?.packageResponseDto.packageType,
        sortable: true,
    },
    {
        name: 'Paket Durumu',
        selector: (row: customerPackageData) => row?.recordStatus,
        sortable: true,
    },
    {
        name: 'Başlangıç Tarihi',
        selector: (row: customerPackageData) => row?.startDateTime,
        sortable: true,
    },
    {
        name: 'Bitiş Tarihi',
        selector: (row: customerPackageData) => row?.finishDateTime,
        sortable: true,
    },
    {
        name: 'Kalan İlan Sayısı',
        selector: (row: customerPackageData) => row?.advertisementCount,
        sortable: true,
    }
  ];

  

  useEffect(() => {
    if (search === '') {
        setFilteredData(customerPackageData);
    } else {
        setFilteredData(customerPackageData?.filter((item) => {
            const itemData = item?.recordStatus.toUpperCase()
            const textData = search.toUpperCase();
            return itemData.indexOf(textData) > -1;
        }))
    }


}, [search, customerPackageData])


useEffect(() => {
  fetchCustomerPackagesData();
}, [])

const fetchCustomerPackagesData = async () => {
  const customerPackageData = await get_all_customer_packages_by_customer_id(customer?.id);
  if (customerPackageData?.success) {
    setCustomerPackageData(customerPackageData?.data);
  }
}


  return (
    <div className='w-full h-full bg-white'>
      <DataTable
        columns={columns}
        data={filteredData || []}
        key={'ThisisCustomerPackagesData'}
        pagination
        keyField="id"
        title={`Paketlerim`} 
        fixedHeader
        fixedHeaderScrollHeight='500px'
        selectableRows
        selectableRowsHighlight
        persistTableHead
        subHeader
        subHeaderComponent={
          <input className='w-60 dark:bg-transparent py-2 px-2  outline-none  border-b-2 border-orange-600' type={"search"}
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder={"Package Name"} />
      }
        className="bg-white px-4 h-4/6 "
      />

    </div>
  )
}

