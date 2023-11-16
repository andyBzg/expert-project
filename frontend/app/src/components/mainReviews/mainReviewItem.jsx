import React from 'react';
import ReactStars from "react-stars/dist/react-stars";
import s from './style.module.css'

const MainReviewItem = ({photo, rating, title, subtitle}) => {
    return (
        <div className={s.reviewItem}>
            <div className={s.reviewItem__img}>
                <img src={photo} alt="customer img"/>
            </div>
            <div className={s.reviewItem__right}>
                <h3 className={s.reviewItem__title}>{title}</h3>
                <ReactStars
                    count={5}
                    size={30}
                    edit={false}
                    isHalf={true}
                    filledIcon={<i className="las la-star"></i>}
                    emptyIcon={<i className="lar la-star"></i>}
                    halfIcon={<i className="las la-star-half-alt"></i>}
                    value={rating}
                    activeColor="#F99A13"
                    color="#F99A13"
                />
                <div className={s.reviewItem__subtitle}>{subtitle}</div>
            </div>
        </div>
    );
};

export default MainReviewItem;
