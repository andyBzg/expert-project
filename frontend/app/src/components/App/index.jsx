import './App.css';
import MainPage from '../../pages/MainPage';
import { Route, Routes } from 'react-router-dom';
import Layout from '../Layout';
import NotFoundPage from '../../pages/NotFoundPage';
import CategoryPage from '../../pages/CategoryPage';


function App() {
  return (
    <div>
      <Routes>
        <Route path='/' element={<Layout />}>
          <Route index element={<MainPage />} />
          <Route path='/ask/:link' element={<CategoryPage />} />
          <Route path='/*' element={<NotFoundPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
