"use Client"

import Cookies from 'js-cookie'
import React, { useEffect, useState } from 'react'
import DataTable from 'react-data-table-component';
import { RootState } from '@/Store/store';
import { useSelector, useDispatch } from 'react-redux'
import { useRouter } from 'next/navigation';
import { get_all_orders_by_customer_id } from '@/Services/order';
import { setUserData } from '@/utils/UserDataSlice'
import { setAddressData } from '@/utils/AddressDataSlice'
import { setCustomerData } from '@/utils/CustomerDataSlice'


type orderData = {
  id: string;
  recordStatus: string,
  createDateTime: Date,
  orderCode: string,
  orderStatus: string,
  customerId: string,
  packageId: string
};

interface customerData {
    id: String,
    userId: String,
    recordStatus: String,
    accountType: String,
    score: Number
}


export default function OrdersDataTable() {
  const router = useRouter();
  const dispatch = useDispatch();
  const [orderData, setOrderData] = useState<orderData[] | []>([]);
  const data = useSelector((state: RootState) => state.Order.order);
  const customer = useSelector((state: RootState) => state.Customer.customerData) as customerData | null

  const [search, setSearch] = useState('');
  const [filteredData, setFilteredData] = useState<orderData[] | []>([]);


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
    setOrderData(data)
  }, [data])



  useEffect(() => {
    setFilteredData(orderData);
  }, [ orderData])


  const columns = [
    {
      name: 'Sipariş Durumu',
      selector: (row: orderData) => row?.orderStatus,
      sortable: true,
    },
    {
        name: 'Sipariş No',
        selector: (row: orderData) => row?.orderCode,
        sortable: true,
    },
    {
        name: 'Sipariş Tarihi',
        selector: (row: orderData) => row?.createDateTime,
        sortable: true,
    },
    {
      name: 'Action',
      cell: (row: orderData) => (
        <div className='flex items-center justify-start px-2 h-20'>
          <button onClick={() => console.log(row?.id)} className=' w-20 py-2 mx-2 text-xs text-green-600 hover:text-white my-2 hover:bg-green-600 border border-green-600 rounded transition-all duration-700'>
            Ödeme Detayı
          </button>
        </div>
      )
    },
  ];  

  useEffect(() => {
    if (search === '') {
        setFilteredData(orderData);
    } else {
        setFilteredData(orderData?.filter((item) => {
            const itemData = item?.orderCode.toUpperCase()
            const textData = search.toUpperCase();
            return itemData.indexOf(textData) > -1;
        }))
    }


}, [search, orderData])


useEffect(() => {
    fetchOrdersData();
}, [])

const fetchOrdersData = async () => {
  const orderData = await get_all_orders_by_customer_id(customer?.id);
  if (orderData?.success) {
    setOrderData(orderData?.data);
  }
}


  return (
    <div className='w-full h-full bg-white'>
      <DataTable
        columns={columns}
        data={filteredData || []}
        key={'ThisisOrdersData'}
        pagination
        keyField="id"
        title={`Sipariş Listesi`} 
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
              placeholder={"Sipariş No"} />
      }
        className="bg-white px-4 h-4/6 "
      />

    </div>
  )
}

