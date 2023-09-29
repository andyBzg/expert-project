import { configureStore } from '@reduxjs/toolkit';
import registrationModalReducer from './slices/registrationModalSlice';
import loginModalReducer from './slices/loginModalslice';
import footerReducer from './slices/footerSlice';
import categoriesSlice from './slices/categoriesSlice';

export const store = configureStore({
  reducer: {
    registrationModal: registrationModalReducer,
    loginModal: loginModalReducer,
    footer: footerReducer,
    categories: categoriesSlice
  }
})
