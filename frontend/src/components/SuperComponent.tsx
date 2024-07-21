import React from 'react'
import { useSelector } from 'react-redux'
import { RootState } from '@/Store/store'
import TileContainer from './TileContainer';
import CPackagesDataTable from './CPackagesDataTable';
import AdvertisementsDataTable from './AdvertisementsDataTable';
import OrdersDataTable from './OrdersDataTable';
import CustomerPackagesDataTable from './CustomerPackagesDataTable';

export default function SuperComponent() {
    const navActive = useSelector((state: RootState) => state.UserNav.ActiveNav)
    switch (navActive) {
        case 'Base':
            return <TileContainer />;
        case 'allPackages':
            return <CPackagesDataTable/>
        case 'allAdvertisements':
            return <AdvertisementsDataTable/>
        case 'allOrders':
            return <OrdersDataTable/>
        case 'allCustomerPackages':
            return <CustomerPackagesDataTable/>
        default:
            return <TileContainer />;
    }
}

export const dynamic = 'force-dynamic'