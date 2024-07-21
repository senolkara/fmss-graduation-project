import { createSlice } from '@reduxjs/toolkit'

interface AddressState {
    addressData: any[]
}

const initialState : AddressState = {
    addressData:  []
}

export const addressSlice = createSlice({
  name: 'Address',
  initialState,
  reducers: {
    setAddressData : (state, action) => {
        state.addressData = action.payload
    }    
  },
})

// Action creators are generated for each case reducer function
export const { setAddressData } = addressSlice.actions

export const AddressReducer =  addressSlice.reducer