import React from 'react';
import s from './style.module.css'
import Container from "../UI/Container";
import {solutionItem} from "../../store/content_data/mainPage.js";
import solutionItemImg from './../../media/solution-stroke.png'


const Solution = () => {
    return (
        <section className={s.solution}>
            <Container className={s.solution__container}>
                <div className={s.solution__wrapper}>
                    <div className={s.solutionHeader}>
                        <div className={s.solutionHeader__title}><h2>Вирішення задачі</h2></div>
                        <div className={s.solutionHeader__subtitle}>yasfdgfhgjk.l qdjhkAFWSGTEHDRTFZGKLU
                                                                    AFSDGHFGJHGSBDHNJ
                        </div>
                    </div>
                    <div className={s.solutionBody}>
                        {
                            solutionItem.map(item =>
                                <div key={item.subtitle} className={s.solutionItem}>
                                    <div className={s.solution__itemImg}>
                                        <img src={solutionItemImg} alt="solution"/>
                                    </div>
                                    <div className={s.solution__itemTitle}>{item.title}</div>
                                    <div className={s.solution__itemSubtitle}>{item.subtitle}</div>
                                </div>
                            )
                        }
                    </div>
                </div>
            </Container>
        </section>
    );
};

export default Solution;
