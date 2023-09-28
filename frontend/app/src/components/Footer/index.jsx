import React from 'react';
import { useSelector } from 'react-redux';
import s from './style.module.css';
import Container from '../UI/Container';
import logo from "../../media/Logo2.jpg";

function Footer() {
  const footerLinks = useSelector((state) => state.footer.links);

  return (
    <Container className={s.container}>
    <footer className={s.footer}>
      <div className={s.logo}>
        <img src={logo} alt="Логотип" />
      </div>
      <ul className={s.links}>
        {footerLinks.map((link) => (
          <li key={link.id}>
            <a href={link.url}>{link.title}</a>
          </li>
        ))}
      </ul>
    </footer>
    </Container>
  );
}

export default Footer;