import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { closeRegistrationModal } from '../../store/slices/registrationModalSlice';
import s from "./style.module.css"

function RegistrationModal() {
  const isOpen = useSelector((state) => state.registrationModal.isOpen);
  const dispatch = useDispatch();

  const handleCloseModal = () => {
    dispatch(closeRegistrationModal());
  };

  if (!isOpen) return null;

  return (
        <div className={s.modal_overlay}>
          <div className={s.modal}>
            <span className={s.close_button} onClick={handleCloseModal}>&times;</span>
            <h2>Регистрация</h2>

            <form>
              <label>Имя:</label>
              <input type="text" className={s.input} />
      
              <label>Email:</label>
              <input type="email" className={s.input} />
      
              <label>Пароль:</label>
              <input type="password" className={s.input} />
      
              {/*поле для капчи здесь */}
              
              <button type="submit" className={s.btn}>ЗАРЕГИСТРИРОВАТЬСЯ</button>
            </form>
            <div>Уже зарегистрированы? <a href="login" className={s.link}>Войти</a></div>
          </div>
        </div>
      );
}

export default RegistrationModal;