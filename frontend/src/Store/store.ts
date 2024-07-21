'use client'


import { UserNavReducer } from '@/utils/UserNavSlice';
import { UserReducer } from '@/utils/UserSlice';
import { UserDataReducer } from '@/utils/UserDataSlice';
import { configureStore } from '@reduxjs/toolkit'
import { OrderReducer } from '@/utils/OrderSlice';
import { CPackageReducer } from '@/utils/CPackageSlice';
import { AddressReducer } from '@/utils/AddressDataSlice';
import { CustomerReducer } from '@/utils/CustomerDataSlice';
import { AdvertisementReducer } from '@/utils/AdvertisementDataSlice';
import { CustomerPackageReducer } from '@/utils/CustomerPackageDataSlice';
import { BuildingReducer } from '@/utils/BuildingDataSlice';



export const store = configureStore({
    reducer: {
        UserData : UserDataReducer,
        Customer: CustomerReducer,
        UserNav : UserNavReducer,
        User : UserReducer,
        Order : OrderReducer,
        CPackage: CPackageReducer,
        Address: AddressReducer,
        Advertisement: AdvertisementReducer,
        CustomerPackage: CustomerPackageReducer,
        Building: BuildingReducer
    },
})

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;