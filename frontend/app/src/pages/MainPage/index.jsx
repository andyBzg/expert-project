import React from 'react'
import Container from '../../components/UI/Container'
import s from './style.module.css'
import Button from '../../components/UI/Button'
import CategoryList from '../../components/CategoryList'
import {reasons} from '../../store/content_data/mainPage.js'
import ReactPlayer from 'react-player'
import {Link} from 'react-router-dom'
import PopCategoryList from "../../components/popCategoryList/popCategoryList";
import ExpertSlider from "../../components/expertsSlider/expertSlider";
import Solution from "../../components/Solution/Solution";

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
                    slidesPerView={3}
                    spaceBetween={88}
                    pagination={{clickable: false}}
                    navigation={true}
                    className={'expertSlider'}
                />
            </Container>
            <Container className={s.container}>
                <div className={s.content_container}>
                    <h2 className={s.subtitle}>Как мы работаем</h2>
                    <div className={s.content}>
                        <div className={s.steps_container}>
                            <Link className={s.step}>
                                <i className={"las la-balance-scale-left"}></i>
                                <div className={s.step_info}>
                                    <span className={s.number}>1</span>
                                    <span className={s.text}>Выберите <br/> категорию</span>
                                </div>
                            </Link>
                            <Link className={s.step}>
                                <i className={"las la-user-plus"}></i>
                                <div className={s.step_info}>
                                    <span className={s.number}>2</span>
                                    <span className={s.text}>Выберите <br/> эксперта</span>
                                </div>
                            </Link>
                            <Link className={s.step}>
                                <i className={"lab la-rocketchat"}></i>
                                <div className={s.step_info}>
                                    <span className={s.number}>3</span>
                                    <span className={s.text}>Общение<br/> с экспертом </span>
                                </div>
                            </Link>
                        </div>
                        <ReactPlayer url="https://vimeo.com/417775951" controls/>
                    </div>
                </div>
            </Container>
            <Container className={s.experts_container}>
        <span className={s.experts_count}>
          сейчас на сайте <strong>80</strong> экспертов
        </span>
            </Container>
        </div>
    )
}
