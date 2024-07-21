"use Client"

import React, { useEffect, useState } from 'react'
import DataTable from 'react-data-table-component';
import { RootState } from '@/Store/store';
import { get_all } from '@/Services/cpackage'
import { useSelector } from 'react-redux'
import { useRouter } from 'next/navigation';



type cpackageData = {
  id: string;
  packageType: string,
  price: Number,
  description: string
};


export default function CPackagesDataTable() {
  const router = useRouter();
  const [cpackageData, setCPackageData] = useState<cpackageData[] | []>([]);
  const data = useSelector((state: RootState) => state.CPackage.cpackage)

  const [search, setSearch] = useState('');
  const [filteredData, setFilteredData] = useState<cpackageData[] | []>([]);

  useEffect(() => {
    setCPackageData(data)
  }, [data])



  useEffect(() => {
    setFilteredData(cpackageData);
  }, [ cpackageData])




  const columns = [
    {
      name: 'Package Type',
      selector: (row: cpackageData) => row?.packageType,
      sortable: true,
    },
    {
        name: 'Price',
        selector: (row: cpackageData) => row?.price,
        sortable: true,
    },
    {
        name: 'Description',
        selector: (row: cpackageData) => row?.description,
        sortable: true,
    },
    {
      name: 'Action',
      cell: (row: cpackageData) => (
        <div className='flex items-center justify-start px-2 h-20'>
          <button onClick={() => router.push(`/order/create-order/${row?.id}`)} className=' w-20 py-2 mx-2 text-xs text-green-600 hover:text-white my-2 hover:bg-green-600 border border-green-600 rounded transition-all duration-700'>Order!</button>
        </div>
      )
    },
  ];

  

  useEffect(() => {
    if (search === '') {
        setFilteredData(cpackageData);
    } else {
        setFilteredData(cpackageData?.filter((item) => {
            const itemData = item?.packageType.toUpperCase()
            const textData = search.toUpperCase();
            return itemData.indexOf(textData) > -1;
        }))
    }


}, [search, cpackageData])


useEffect(() => {
  fetchCPackagesData();
}, [])

const fetchCPackagesData = async () => {
  const cpackageData = await get_all();
  if (cpackageData?.success) {
    setCPackageData(cpackageData?.data);
  }
}


  return (
    <div className='w-full h-full bg-white'>
      <DataTable
        columns={columns}
        data={filteredData || []}
        key={'ThisisCPackagesData'}
        pagination
        keyField="id"
        title={`Satınalınabilir Paketler`} 
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

