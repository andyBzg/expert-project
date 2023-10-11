import React from 'react';
import { Link } from 'react-router-dom';
import s from './style.module.css';

const ExpertCard = ({ expert }) => {
  return (
    <div className={s.card}>
      {/* <img src={expert.photo} alt={`${expert.firstName} ${expert.lastName}`} className={s.photo} /> */}
      <div className={s.details}>
        <h3>{expert.firstName} {expert.lastName}</h3>
        <p>{expert.country}</p>
        <div className={s.rating}>
          <span className={s.ratingLabel}>Рейтинг:</span>
          <span>{expert.rating}</span>
        </div>
        <p>Отзывы: {expert.reviews}</p>
        <p>Область работы: {expert.areaOfWork}</p>
        <p>{expert.description}</p>
        <Link to={`/experts/${expert.id}`} className={s.profileLink}>Профиль</Link>
        <Link to={`/chat/${expert.id}`} className={s.chatLink}>Написать</Link>
      </div>
    </div>
  );
};

export default ExpertCard;