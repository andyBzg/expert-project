import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {fetchExperts} from "../../../store/slices/expertSlice";
import ExpertSliderItem from "../expertSliderItem/expertSliderItem";
import {Navigation} from "swiper/modules";
import {Swiper, SwiperSlide} from "swiper/react";

const ExpertSliderList = ({slidesQuantity, slidesPerView, spaceBetween, pagination, navigation, className}) => {
    const dispatch = useDispatch();
    const expertsArr = useSelector((state) => state.experts.experts);
    const experts = expertsArr.slice()
    experts.splice(slidesQuantity)
    
    useEffect(() => {
        // Вызываем экшен fetchExperts, чтобы отправить запрос на сервер
        dispatch(fetchExperts());
    }, [dispatch]);
    return (
        <>
            <Swiper
                slidesPerView={slidesPerView}
                spaceBetween={spaceBetween}
                pagination={pagination}
                navigation={navigation}
                modules={[Navigation]}
                className={className}
            >
                {
                    experts.map(expert =>
                        <SwiperSlide key={expert.id}>
                            <ExpertSliderItem expert={expert}/>
                        </SwiperSlide>
                    )}
            </Swiper>
        </>
    );
};

export default ExpertSliderList;
