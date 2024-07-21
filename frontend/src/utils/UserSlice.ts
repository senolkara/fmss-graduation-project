import { createSlice } from '@reduxjs/toolkit'

interface NavState {
    cpackage : any[],
    order : any[],
    advertisement: any[],
    customerPackage: any[]
}

const initialState : NavState = {
    cpackage : [],
    order : [],
    advertisement: [],
    customerPackage: []
}

export const User = createSlice({
  name: 'UserData',
  initialState,
  reducers: {
    setCPackageData : (state, action) => {
        state.cpackage = action.payload
    },
    setOrderData : (state, action) => {
        state.order = action.payload
    },
    setAdvertisementData : (state , action) => {
      state.advertisement = action.payload
    },
    setCustomerPackageData : (state , action) => {
      state.customerPackage = action.payload
    }
  },
})

// Action creators are generated for each case reducer function
export const { setCPackageData ,setOrderData ,setAdvertisementData ,setCustomerPackageData } = User.actions

export const UserReducer =  User.reducer