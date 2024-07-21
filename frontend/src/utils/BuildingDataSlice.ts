import { createSlice } from '@reduxjs/toolkit'

interface BuildingState {
    building : any[],
    buildingDetailData: {} | null
}

const initialState : BuildingState = {
    building : [],
    buildingDetailData: null
}

export const buildingSlice = createSlice({
  name: 'Building',
  initialState,
  reducers: {
    setBuildingData : (state, action) => {
        state.building = action.payload
    },
    setBuildingDetailData : (state, action) => {
        state.buildingDetailData = action.payload
    }
  },
})

// Action creators are generated for each case reducer function
export const { setBuildingData, setBuildingDetailData } = buildingSlice.actions

export const BuildingReducer =  buildingSlice.reducer

