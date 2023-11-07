const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3003; // Порт, на котором будет работать сервер
app.use(cors());
// Промежуточное ПО для разбора JSON-запросов
app.use(bodyParser.json());


const experts = [

    {
        id: 1,
        rating: 5,
        firstName: 'Иван Петров',
        description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
        photoURL: 'image1.jpg',
        country: 'Германия',
        url: '#!',
        online: true
    },
    {
        id: 2,
        rating: 4.5,
        firstName: 'Елена Сидорова',
        description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
        photoURL: 'image3.png',
        country: 'Бельгия',
        url: '#!',
        online: true
    },
    {
        id: 3,
        rating: 3,
        firstName: 'Василий Петров',
        description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
        photoURL: 'image.png',
        country: 'Швейцария',
        url: '#!',
        online: false
    },
    {
        id: 4,
        rating: 4,
        firstName: 'Kim',
        description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
        photoURL: 'image4.png',
        country: 'Австрия',
        url: '#!',
        online: false
    },
    {
        id: 2,
        rating: 4.5,
        firstName: 'Anna',
        description: 'Семейные, трудовые, любовные отношения, а также вопросы личностного развития.',
        photoURL: 'image3.png',
        country: 'Германия',
        url: '#!',
        online: true
    },
];


// Роут для получения списка экспертов
app.get('/experts', (req, res) => {
    res.json(experts);
});

// Запуск сервера
app.listen(port, () => {
    console.log(`Сервер запущен на порту ${port}`);
});
