import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

// Создаем экшен с помощью createAsyncThunk
export const fetchExperts = createAsyncThunk('experts/fetchExperts', async () => {
  // Отправляем GET-запрос на сервер и получаем данные
  const response = await fetch('http://localhost:3003/experts'); // Замените URL на ваш сервер
  const data = await response.json();
  return data;
});

// Добавьте обработку данных и состояния экшена в ваш slice
const expertSlice = createSlice({
  name: 'experts',
  initialState: {
    experts: [], // В этом поле будет храниться список экспертов
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