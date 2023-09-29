import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  // hardcode - change with fetch request 
  list: [
    { categoryId: 1, title: 'Психология', icon: '', link: 'personal' },
    { categoryId: 2, title: 'Эзотерика', icon: '', link: 'religion' },
    { categoryId: 3, title: 'Юриспруденция', icon: '', link: 'law' },
    { categoryId: 4, title: 'Учеба и наука', icon: '', link: 'study' },
    { categoryId: 5, title: 'IT Технологии', icon: '', link: 'it' },
    // { categoryId: 6, title: 'Красота и здоровье', icon: '', link: 'beauty' },
    // { categoryId: 7, title: 'Бизнес', icon: '', link: 'bussines' },
    // { categoryId: 8, title: 'Авто', icon: '', link: 'autos' },
    { categoryId: 9, title: 'Другое', icon: '', link: 'other' },
  ]
};

const categoriesSlice = createSlice({
  name: 'categories',
  initialState,
  reducers: {},
});

export default categoriesSlice.reducer;