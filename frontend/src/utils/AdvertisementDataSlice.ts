import { createSlice } from '@reduxjs/toolkit'

interface AdvertisementState {
    advertisement : any[],
    advertisementDetailData: {} | null
}

const initialState : AdvertisementState = {
    advertisement : [],
    advertisementDetailData: null
}

export const advertisementSlice = createSlice({
  name: 'Advertisement',
  initialState,
  reducers: {
    setAdvertisementData : (state, action) => {
        state.advertisement = action.payload
    },
    setAdvertisementDetailData : (state, action) => {
        state.advertisementDetailData = action.payload
    }
  },
})

// Action creators are generated for each case reducer function
export const { setAdvertisementData, setAdvertisementDetailData } = advertisementSlice.actions

export const AdvertisementReducer =  advertisementSlice.reducer

