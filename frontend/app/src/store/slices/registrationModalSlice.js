import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isOpen: false,
};

const registrationModalSlice = createSlice({
  name: 'registrationModal',
  initialState,
  reducers: {
    openRegistrationModal: (state) => {
      state.isOpen = true;
    },
    closeRegistrationModal: (state) => {
      state.isOpen = false;
    },
  },
});

export const { openRegistrationModal, closeRegistrationModal } = registrationModalSlice.actions;

export default registrationModalSlice.reducer;