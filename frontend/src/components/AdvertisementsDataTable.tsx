"use Client"

import Cookies from 'js-cookie'
import React, { useEffect, useState } from 'react'
import Link from 'next/link'
import DataTable from 'react-data-table-component';
import { RootState } from '@/Store/store';
import { useSelector, useDispatch } from 'react-redux'
import { useRouter } from 'next/navigation';
import { delete_a, get_all_advertisements_by_customer_id } from '@/Services/advertisement';
import { toast } from 'react-toastify'
import { setUserData } from '@/utils/UserDataSlice'
import { setAddressData } from '@/utils/AddressDataSlice'
import { setCustomerData } from '@/utils/CustomerDataSlice'
import AdvertisementCreateModal from './AdvertisementCreateModal';


type advertisementData = {
  id: string;
  advertisementType: string,
  advertisementStatus: string,
  advertisementNo: string,
  startDateTime: Date,
  finishDateTime: Date,
  price: Number,
  buildingResponseDto: {
    id: string,
    name: string
  },
  customerPackageResponseDto: {
    id: string,
    packageResponseDto: {
      packageType: string
    }
  }
  customerPackageId: string
};

interface customerData {
    id: String,
    userId: String,
    recordStatus: String,
    accountType: String,
    score: Number
}


export default function AdvertisementsDataTable() {
  const router = useRouter();
  const dispatch = useDispatch();
  const [advertisementData, setAdvertisementData] = useState<advertisementData[] | []>([]);
  const data = useSelector((state: RootState) => state.Advertisement.advertisement);
  const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null

  const [search, setSearch] = useState('');
  const [filteredData, setFilteredData] = useState<advertisementData[] | []>([]);


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

  useEffect(() => {
    setAdvertisementData(data)
  }, [data])



  useEffect(() => {
    setFilteredData(advertisementData);
  }, [ advertisementData])


  const columns = [
    {
      name: 'İlan Tipi',
      selector: (row: advertisementData) => row?.advertisementType,
      sortable: true,
    },
    {
        name: 'İlan Durumu',
        selector: (row: advertisementData) => row?.advertisementStatus,
        sortable: true,
    },
    {
        name: 'İlan No',
        selector: (row: advertisementData) => row?.advertisementNo,
        sortable: true,
    },
    {
      name: 'Paket Tipi',
      selector: (row: advertisementData) => row?.customerPackageResponseDto.packageResponseDto.packageType,
      sortable: true,
    },
    {
      name: 'Yapı',
      selector: (row: advertisementData) => row?.buildingResponseDto.name,
      sortable: true,
    },
    {
      name: 'Bitiş Tarihi',
      selector: (row: advertisementData) => row?.finishDateTime,
      sortable: true,
    },
    {
        name: 'Tutar',
        selector: (row: advertisementData) => row?.price,
        sortable: true,
    },
    {
      name: 'Action',
      cell: (row: advertisementData) => (
        <div className='flex items-center justify-start px-2 h-20'>
          <button onClick={() => router.push(`/advertisement/update/${row?.id}`)} className=' w-20 py-2 mx-2 text-xs text-green-600 hover:text-white my-2 hover:bg-green-600 border border-green-600 rounded transition-all duration-700'>
            Düzenle
          </button>
          {/*<button onClick={() => handleDeleteAdvertisement(row?.id)} className=' w-20 py-2 mx-2 text-xs text-red-600 hover:text-white my-2 hover:bg-red-600 border border-red-600 rounded transition-all duration-700'>
            Delete
          </button>*/}
        </div>
      )
    },
  ];

  const handleDeleteAdvertisement = async (id: string) => {
    if (confirm("Emin misiniz?")) {
      const res = await delete_a(id);
      if (res?.success) {
        toast.success(res?.message);
        fetchAdvertisementsData();
      }
      else {
        toast.error(res?.message)
      }
    }
  }

  

  useEffect(() => {
    if (search === '') {
        setFilteredData(advertisementData);
    } else {
        setFilteredData(advertisementData?.filter((item) => {
            const itemData = item?.advertisementType.toUpperCase()
            const textData = search.toUpperCase();
            return itemData.indexOf(textData) > -1;
        }))
    }

}, [search, advertisementData])


useEffect(() => {
  fetchAdvertisementsData();
}, [])

const fetchAdvertisementsData = async () => {
  const advertisementData = await get_all_advertisements_by_customer_id(customer?.id);
  if (advertisementData?.success) {
    setAdvertisementData(advertisementData?.data);
  }
}


  return (
    <div className='w-full h-full bg-white'>
      <div className='float-right'>
        <Link href="/dashboard?advertisement_create_modal=true">
            <button type="button" className="bg-blue-500 text-white p-2">İlan Oluştur</button>
        </Link>
      </div>
      <DataTable
        columns={columns}
        data={filteredData || []}
        key={'ThisisAdvertisementsData'}
        pagination
        keyField="id"
        title={`İlan Listesi`} 
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
              placeholder={"İlan No"} />
      }
        className="bg-white px-4 h-4/6 "
      />

      <AdvertisementCreateModal/>
    </div>
  )
}

