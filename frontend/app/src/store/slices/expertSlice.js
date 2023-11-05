import {createSlice, createAsyncThunk} from '@reduxjs/toolkit';

// Создаем экшен с помощью createAsyncThunk
export const fetchExperts = createAsyncThunk('experts/fetchExperts', async () => {
    // Отправляем GET-запрос на сервер и получаем данные
    const response = await fetch('http://localhost:3003/experts'); // Замените URL на ваш сервер
    const data = await response.json();
    return data;
});


const expertSlice = createSlice({
    name: 'experts',
    initialState: {
        experts: [
            {
                id: 1,
                rating: 5,
                firstName: 'Bob',
                description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
                photo: 'image1.jpg',
                url: '#!',
                online: true
            },
            {
                id: 2,
                rating: 4.5,
                firstName: 'Anna',
                description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
                photo: 'image3.png',
                url: '#!',
                online: true
            },
            {
                id: 3,
                rating: 3,
                firstName: 'John',
                description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
                photo: 'image.png',
                url: '#!',
                online: false
            },
            {
                id: 4,
                rating: 4,
                firstName: 'Kim',
                description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
                photo: 'image.png',
                url: '#!',
                online: false
            },
            {
                id: 2,
                rating: 4.5,
                firstName: 'Anna',
                description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
                photo: 'image3.png',
                url: '#!',
                online: true
            },
        ], // В этом поле будет храниться список экспертов
        status: 'idle', // Статус запроса (idle, loading, succeeded, failed)
        error: null, // Ошибка, если запрос завершился с ошибкой
    },
    reducers: {
        // Другие редукс-действия, если они вам нужны
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchExperts.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchExperts.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.experts = action.payload;
            })
            .addCase(fetchExperts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    },
});

export default expertSlice.reducer;
