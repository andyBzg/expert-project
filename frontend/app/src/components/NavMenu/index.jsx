import React from 'react';
import Container from '../UI/Container';
import logo from "../../media/23-removebg-preview.png";
import { Link } from 'react-router-dom';
import s from "./style.module.css";
import RegistrationModal from '../RegistrationModal';
import { useDispatch } from 'react-redux';
import { openRegistrationModal } from '../../store/slices/registrationModalSlice';
import LoginModal from '../LoginModal';
import { openLoginModal } from '../../store/slices/loginModalslice';

export default function NavMenu() {

  const dispatch = useDispatch();

  const handleOpenRegistrationModal = () => {
    dispatch(openRegistrationModal());
  };

  const handleOpenLoginModal = () => {
    dispatch(openLoginModal());
  };
 

  return (
    <Container className={s.container}>
      <Link to="/">
              <div className={s.logo}>
                <img src={logo} alt="Logo" />
              </div>
      </Link>
      <div className={s.registration_login}>
        <div>
          <button className={s.btn} onClick={handleOpenRegistrationModal}>РЕГИСТРАЦИЯ</button>
          <RegistrationModal/>
        </div>
        <div>
          <button className={s.btn} onClick={handleOpenLoginModal}>ВОЙТИ</button>
          <LoginModal/>
        </div>
      </div>
    </Container>
  )
}
