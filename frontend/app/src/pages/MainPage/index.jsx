import React from 'react'
import Container from '../../components/UI/Container'
import s from './style.module.css'
import Button from '../../components/UI/Button'
import CategoryList from '../../components/CategoryList'
import ReactPlayer from 'react-player'
import {Link} from 'react-router-dom'
import PopCategoryList from "../../components/popCategoryList/popCategoryList";
import ExpertSlider from "../../components/expertsSlider/expertSlider";
import Solution from "../../components/Solution/Solution";
import InfoBlock from "../../components/InfoBlock/infoBlock";

export default function MainPage() {
    return (
        <div className={s.page}>
            <Container className={s.poster}>
                <h1 className={s.title}>Есть вопрос? Задай эксперту!</h1>
                <div className={s.buttons}>
                    <Button variant={"primary"} size={'size_xl'} uppercase={'uppercase'} to={'/'}>
                        задать вопрос
                    </Button>
                    <Button variant={"outlined"} size={'size_xl'} to={'/'}>
                        Заданные вопросы
                    </Button>
                </div>
            </Container>
            <CategoryList/>
            <PopCategoryList/>
            <Solution/>
            <Container>
                <ExpertSlider
                    slidesQuantity={8}
                    slidesPerView={3}
                    spaceBetween={88}
                    pagination={{clickable: false}}
                    navigation={true}
                    className={'expertSlider'}
                />
            </Container>
            <section className={s.howIsWorks}>
                <Container className={s.howIsWorks__container}>
                    <div className={s.howIsWorks__wrapper}>
                        <h2 className={s.howIsWorks__title}>Як це працює</h2>
                        <div className={s.howIsWorks__subtitle}>qeawrsetrdzhtfujzgkumhn AWSEGDHRFTJGZH,
                                                                JLGKFBDJSVEDWNESL
                                                                sedgfrhgnhjbvcxxdcfdvgbhnhmgnfdtgkrsjefawd
                        </div>
                    </div>
                    <ReactPlayer width={'750px'} height={'440px'} url="https://vimeo.com/417775951" controls/>
                </Container>
            </section>
            <InfoBlock/>

            <Container className={s.experts_container}>
        <span className={s.experts_count}>
          сейчас на сайте <strong>80</strong> экспертов
        </span>
            </Container>
        </div>
    )
}
