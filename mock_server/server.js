const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3003; // Порт, на котором будет работать сервер
app.use(cors());
// Промежуточное ПО для разбора JSON-запросов
app.use(bodyParser.json());

// Эмуляция данных экспертов (замените этим вашими данными из базы данных)
const experts = [
  { id: 1, name: 'Иван Петров', country: 'Россия' },
  { id: 2, name: 'Елена Сидорова', country: 'Украина' },
];

// Роут для получения списка экспертов
app.get('/experts', (req, res) => {
  res.json(experts);
});

// Запуск сервера
app.listen(port, () => {
  console.log(`Сервер запущен на порту ${port}`);
});