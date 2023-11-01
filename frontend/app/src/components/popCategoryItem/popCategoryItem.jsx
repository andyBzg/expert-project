import React from 'react';
import s from './style.module.css'
import {Link} from "react-router-dom";


const PopCategoryItem = ({img, title, link}) => {
    const imgURL = require(`../../media/ImgCategory/${img}`)
    return (
        <div className={s.popCategories__item}>
            <h3>
                <Link className={s.popCategories__link} to={link}>{title}</Link>
            </h3>
            <div className={s.popCategories__img}
                 style={{backgroundImage: `url(${imgURL})`}}></div>
            <div className={s.popCategories__overlay}></div>
        </div>
    );
};

export default PopCategoryItem;
