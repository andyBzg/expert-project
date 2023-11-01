import {createSlice} from '@reduxjs/toolkit';

const initialState = {
    // hardcode - change with fetch request
    list: [
        {categoryId: 1, title: 'Технології IT', icon: 'technology.png', link: 'it', img: 'technology.png'},
        {categoryId: 2, title: 'Юриспруденція', icon: 'law.png', link: 'law', img: 'law.png'},
        {categoryId: 3, title: 'Психология', icon: 'personal.png', link: 'personal', img: 'personal.png'},
        {categoryId: 4, title: 'Навчання та наука', icon: 'study.png', link: 'study', img: 'study.png'},
        {categoryId: 5, title: 'Езотерика', icon: 'religion.png', link: 'religion', img: 'religion.png'},
        // { categoryId: 6, title: 'Красота и здоровье', icon: '', link: 'beauty' },
        // { categoryId: 7, title: 'Бизнес', icon: '', link: 'bussines' },
        // { categoryId: 8, title: 'Авто', icon: '', link: 'autos' },
        {categoryId: 9, title: 'Усі категорії', icon: 'other.png', link: 'other', img: 'other.png'},
    ]
};

const categoriesSlice = createSlice({
    name: 'categories',
    initialState,
    reducers: {},
});

export default categoriesSlice.reducer;
