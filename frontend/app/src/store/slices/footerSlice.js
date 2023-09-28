import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  // Здесь можете хранить состояние футера, например, ссылки.
  links: [
    { id: 1, title: 'Правила оплаты', url: '/payment-rules' },
    { id: 2, title: 'Вопрос-Ответ', url: '/faq' },
    { id: 3, title: 'Свяжитесь с нами', url: '/contact-us' },
    { id: 4, title: 'О проекте', url: '/about-us' },
    { id: 5, title: 'Пользовательское соглашение', url: '/terms-of-service' },
    { id: 6, title: 'Политика конфиденциальности', url: '/privacy-policy' },
    { id: 7, title: 'Политика использования cookie', url: '/cookie-policy' },
    { id: 8, title: 'Карта сайта', url: '/site-map' },
    { id: 9, title: 'Стать экспертом', url: '/become-expert' },
    { id: 10, title: 'Стать партнером', url: '/become-partner' },
  ],
};

const footerSlice = createSlice({
  name: 'footer',
  initialState,
  reducers: {},
});

export default footerSlice.reducer;