import { createSlice } from '@reduxjs/toolkit'

interface CustomerState {
    customerData:  {} | null,
    customerUserData: {} | null
}

const initialState : CustomerState = {
    customerData:  null,
    customerUserData: null
}

export const customerSlice = createSlice({
  name: 'Customer',
  initialState,
  reducers: {
    setCustomerData : (state, action) => {
        state.customerData = action.payload
    },
    setCustomerUserData : (state, action) => {
        state.customerUserData = action.payload
    }
  },
})

// Action creators are generated for each case reducer function
export const { setCustomerData, setCustomerUserData } = customerSlice.actions

export const CustomerReducer =  customerSlice.reducer