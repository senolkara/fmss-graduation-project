import { createSlice } from '@reduxjs/toolkit'

interface CPackageState {
    cpackage : any[]
    cpackageDetailData: {} | null
}

const initialState : CPackageState = {
    cpackage : [],
    cpackageDetailData: null
}

export const cpackageSlice = createSlice({
  name: 'CPackage',
  initialState,
  reducers: {
    setCPackageData : (state, action) => {
        state.cpackage = action.payload
    },
    setCPackageDetailData : (state, action) => {
        state.cpackageDetailData = action.payload
    }
  },
})

// Action creators are generated for each case reducer function
export const { setCPackageData, setCPackageDetailData } = cpackageSlice.actions

export const CPackageReducer =  cpackageSlice.reducer

