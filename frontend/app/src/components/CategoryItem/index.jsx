import React from 'react';
import s from './style.module.css'
import { Link } from 'react-router-dom'

const CategoryItem = ({ icon, title, link }) => {

    return (
        <Link to={`/ask/${link}`} className={s.item}>
            <div className={s.category}>
                <i class="las la-atom"></i> 
                {/* icon will be chanched for each item */}
                <h5 className={s.title}>{title} <span></span></h5>
            </div>
        </Link>
    );
};

export default CategoryItem;