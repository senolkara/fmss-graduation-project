import { createSlice } from '@reduxjs/toolkit'

interface NavState {
    ActiveNav : string,
}

const initialState : NavState = {
    ActiveNav : 'Base'
}

export const UserNav = createSlice({
  name: 'UserNav',
  initialState,
  reducers: {
    setNavActive : (state, action) => {
        state.ActiveNav = action.payload
    }

    
  },
})

// Action creators are generated for each case reducer function
export const { setNavActive} = UserNav.actions

export const UserNavReducer =  UserNav.reducer