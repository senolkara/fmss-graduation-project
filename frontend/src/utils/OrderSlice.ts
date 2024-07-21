import { createSlice } from '@reduxjs/toolkit'


interface Data {
    order: [],
    orderDetailData: {} | null
}


const initialState: Data = {
    order: [],
    orderDetailData: {}
}


export const OrderSlice = createSlice({
    name: 'Order',
    initialState,
    reducers: {
        setOrderData: (state, action) => {
            state.order =  action.payload
        },
        setOrderDetailData: (state, action) => {
            state.orderDetailData =  action.payload
        }
    },
})

export const {setOrderData, setOrderDetailData  } = OrderSlice.actions

export const OrderReducer = OrderSlice.reducer;