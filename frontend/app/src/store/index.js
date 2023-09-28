import { configureStore } from '@reduxjs/toolkit';
import registrationModalReducer from './slices/registrationModalSlice';
import loginModalReducer from './slices/loginModalslice';
import footerReducer from './slices/footerSlice';

export const store = configureStore({
  reducer: {
    registrationModal: registrationModalReducer,
    loginModal: loginModalReducer,
    footer: footerReducer,
  }
})
