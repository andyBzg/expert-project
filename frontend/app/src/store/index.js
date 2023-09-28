import { configureStore } from '@reduxjs/toolkit';
import registrationModalReducer from './slices/registrationModalSlice';
import loginModalReducer from './slices/loginModalslice';

export const store = configureStore({
  reducer: {
    registrationModal: registrationModalReducer,
    loginModal: loginModalReducer,
  }
})
