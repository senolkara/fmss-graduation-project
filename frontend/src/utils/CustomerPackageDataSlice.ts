import { createSlice } from '@reduxjs/toolkit'

interface CustomerPackageState {
    customerPackage : any[]
    customerPackageDetailData: {} | null
}

const initialState : CustomerPackageState = {
    customerPackage : [],
    customerPackageDetailData: null
}

export const customerPackageSlice = createSlice({
  name: 'CustomerPackage',
  initialState,
  reducers: {
    setCustomerPackageData : (state, action) => {
        state.customerPackage = action.payload
    },
    setCustomerPackageDetailData : (state, action) => {
        state.customerPackageDetailData = action.payload
    }
  },
})

// Action creators are generated for each case reducer function
export const { setCustomerPackageData, setCustomerPackageDetailData } = customerPackageSlice.actions

export const CustomerPackageReducer =  customerPackageSlice.reducer

