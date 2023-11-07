import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {fetchExperts} from "../../../store/slices/expertSlice";
import ExpertSliderItem from "../expertSliderItem/expertSliderItem";
import {Navigation} from "swiper/modules";
import {Swiper, SwiperSlide} from "swiper/react";

const ExpertSliderList = () => {
    const dispatch = useDispatch();
    const experts = useSelector((state) => state.experts.experts);

    useEffect(() => {
        // Вызываем экшен fetchExperts, чтобы отправить запрос на сервер
        dispatch(fetchExperts());
    }, [dispatch]);
    return (
        <>
            <Swiper
                slidesPerView={3}
                spaceBetween={88}
                pagination={{
                    clickable: true,
                }}
                navigation={true}
                modules={[Navigation]}
                className="mySwiper"
            >
                {
                    experts.map((expert, index) =>
                        <SwiperSlide key={index}>
                            <ExpertSliderItem expert={expert}/>
                        </SwiperSlide>
                    )
                }

            </Swiper>

        </>
    );
};

export default ExpertSliderList;
