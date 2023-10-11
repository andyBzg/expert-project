import { configureStore } from '@reduxjs/toolkit';
import registrationModalReducer from './slices/registrationModalSlice';
import loginModalReducer from './slices/loginModalslice';
import footerReducer from './slices/footerSlice';
import categoriesSlice from './slices/categoriesSlice';
import expertReducer from './slices/expertSlice';

export const store = configureStore({
  reducer: {
    registrationModal: registrationModalReducer,
    loginModal: loginModalReducer,
    footer: footerReducer,
    categories: categoriesSlice,
    experts: expertReducer,
  }
})
