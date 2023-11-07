import React from 'react';
import {Link} from "react-router-dom";
import s from './style.module.css'
import ReactStars from "react-stars/dist/react-stars";


const ExpertSliderItem = ({expert}) => {
    const photoURL = require(`../../../media/expertsPhoto/${expert.photo}`)
    return (

        <div className={s.expert__card}>
            {expert.online
                ? <div className={s.expert__online}><span></span> Онлайн</div>
                : <div className={s.expert__offline}><span></span> Офлайн</div>
            }

            <div className={s.expert__img}>
                <img src={photoURL} alt={expert.firstName}/>
            </div>
            <div className={s.expert__name}>{expert.firstName}</div>
            <div className={s.expert__rating}>
                {/*https://github.com/ertanhasani/react-stars*/}
                <ReactStars
                    count={5}
                    size={30}
                    edit={false}
                    isHalf={true}
                    filledIcon={<i className="las la-star"></i>}
                    emptyIcon={<i className="lar la-star"></i>}
                    halfIcon={<i className="las la-star-half-alt"></i>}
                    value={expert.rating}
                    activeColor="#F99A13"
                    color="#F99A13"
                />
            </div>
            <div className={s.expert__descriptions}>{expert.description}</div>
            <div className={s.expert__about}><Link to={'#'}>Читати більше</Link></div>
        </div>

    );
};

export default ExpertSliderItem;
