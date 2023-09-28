import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { closeLoginModal } from '../../store/slices/loginModalslice';
import s from "./style.module.css"

function LoginModal() {
  const isOpen = useSelector((state) => state.loginModal.isOpen);
  const [rememberMe, setRememberMe] = useState(false);
  const dispatch = useDispatch();

  const handleCloseModal = () => {
    dispatch(closeLoginModal());
  };

  if (!isOpen) return null;

  return (
        <div className={s.modal_overlay}>
          <div className={s.modal}>
            <span className={s.close_button} onClick={handleCloseModal}>&times;</span>
            <h2>Войти</h2>
            
            <form>
            
              <label>Email:</label>
              <input type="email" className={s.input} />
      
              <label>Пароль:</label>
              <input type="password" className={s.input} />
              <label>
                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={() => setRememberMe(!rememberMe)}
                />
                Запомнить меня на этом компьютере
              </label>
             
              
              <button type="submit" className={s.btn}>ВОЙТИ</button>
            </form>
            <div>Уже зарегистрированы? <a href="login" className={s.link}>Войти</a></div>
          </div>
        </div>
      );
}

export default LoginModal;