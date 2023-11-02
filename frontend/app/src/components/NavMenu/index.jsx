import React from 'react';
import Container from '../UI/Container';
import logo_text from "../../media/Ask_logo_text.png";
import logo_image from "../../media/ASK_VALISSA__1_-removebg-preview 1.png";
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
                <div className={s.logo_image}>
                  <img src={logo_image} alt="Logo_image" />
                </div>
                <div className={s.logo_text}>
                  <img src={logo_text} alt="Logo_text" />
                  <p>online freelance platform</p>
                </div>
              </div>
      </Link>
      <div className={s.registration_login}>
        <div>
          <p>про нас</p>
        </div>
        <div>
          <button className={s.btn} onClick={handleOpenLoginModal}>ВОЙТИ</button>
          <LoginModal/>
        </div>
        <div>
          <button className={s.btn} onClick={handleOpenRegistrationModal}>РЕГИСТРАЦИЯ</button>
          <RegistrationModal/>
        </div>
      </div>
    </Container>
  )
}
