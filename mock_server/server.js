const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3003; // Порт, на котором будет работать сервер
app.use(cors());
// Промежуточное ПО для разбора JSON-запросов
app.use(bodyParser.json());


const experts = [
  { id: 1, firstName: 'Иван Петров', country: 'Россия' },
  { id: 2, firstName: 'Елена Сидорова', country: 'Украина' },
  { id: 3, firstName: 'Василий Сергеевич', country: 'Германия'},
];

// Роут для получения списка экспертов
app.get('/experts', (req, res) => {
  res.json(experts);
});

// Запуск сервера
app.listen(port, () => {
  console.log(`Сервер запущен на порту ${port}`);
});