import React from 'react';
import 'swiper/css';
import "./style.css";
import ExpertSliderList from "./expertSliderList/expertSliderList";

const ExpertSlider = (config) => {

    return (
        <div className={`slider`}>
            <div className={`slider__container`}>
                <ExpertSliderList {...config}/>
            </div>
        </div>
    );
};

export default ExpertSlider;
