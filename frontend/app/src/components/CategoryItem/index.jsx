import React from 'react';
import s from './style.module.css'
import icon from './atom.svg'
import { Link } from 'react-router-dom'

const CategoryItem = ({ img, title, link }) => {
    return (
        <Link to={`/expert/${link}`} className={s.item}>
            <div className={s.category}>
                <img className={s.icon} src={icon} alt={title} />
                <h5 className={s.title}>{title}</h5>
            </div>
        </Link>
    );
};

export default CategoryItem;