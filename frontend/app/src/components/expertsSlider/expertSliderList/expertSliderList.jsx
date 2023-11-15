import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {fetchExperts} from "../../../store/slices/expertSlice";
import ExpertSliderItem from "../expertSliderItem/expertSliderItem";
import {Navigation} from "swiper/modules";
import {Swiper, SwiperSlide} from "swiper/react";

const ExpertSliderList = ({slidesPerView, spaceBetween, pagination, navigation, className}) => {
    const dispatch = useDispatch();
    const experts = useSelector((state) => state.experts.experts);

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
                    )
                }

            </Swiper>

        </>
    );
};

export default ExpertSliderList;