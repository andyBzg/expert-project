import React from 'react';
import s from './style.module.css'
import {mainReviews} from "../../store/content_data/mainPage";
import Container from "../UI/Container";
import MainReviewItem from "./mainReviewItem";
import {Link} from "react-router-dom";
import Button from "../UI/Button";

const MainReviews = () => {

    return (
        <Container>
            <section className={s.review}>
                <h2 className={s.review__title}>Відгуки</h2>
                <div className={s.reviewItem__wrapper}>
                    {
                        mainReviews.map((review, index) =>
                            <MainReviewItem
                                title={review.title}
                                subtitle={review.subtitle}
                                rating={review.rating}
                                key={index}
                                photo={require(`./../../media/photo-profile${index + 1}.jpg`)}/>
                        )
                    }
                </div>
                <Button
                    rounded={'rounded'}
                    uppercase={'uppercase'}
                    variant={'secondary'}
                    size={'size_m'}
                    to={`#`}>
                    Дивитись більше</Button>
            </section>
        </Container>
    );
};

export default MainReviews;
// width: Fixed (230px)
// height: Fixed (40px)
// top: 339px
// left: 645px
// padding: 16px, 24px, 16px, 24px
// border-radius: 104px
// gap: 8px
