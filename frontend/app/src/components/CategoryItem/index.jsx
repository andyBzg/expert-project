import React from 'react';
import s from './style.module.css'
import {Link} from 'react-router-dom'

const CategoryItem = ({icon, title, link}) => {
    const icoURL = require(`../../pages/MainPage/ico_category/${icon}`)
    return (
        <Link to={`/ask/${link}`} className={s.item}>
            <div className={s.category}>
                <div className={s.ico__wrapper}>
                    <img alt={title} src={icoURL}/>
                    <div className={s.clear}></div>
                </div>
                {/* icon will be chanched for each item */}
                <div className={s.title__wrapper}>
                    <h5 className={s.title}>{title}</h5> <span className={s.chevron__down}>
                    </span>
                </div>
            </div>
        </Link>
    );
};

export default CategoryItem;
