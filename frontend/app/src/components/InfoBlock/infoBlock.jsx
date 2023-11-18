import React from 'react';
import s from './style.module.css'
import Container from "../UI/Container";
import {infoBlockData} from "../../store/content_data/mainPage";
import infoBlockImg1 from './../../media/rafiki.png'
import infoBlockImg2 from './../../media/rafiki2.png'
import infoBlockImg3 from './../../media/rafiki3.png'
import {Link} from "react-router-dom";
import {useInView} from "react-intersection-observer";


const InfoBlock = () => {

    const {ref, inView} = useInView({
        /* Optional options */
        threshold: 0.1,
    });

    let classes = [s.infoBlock__item]
    if (inView) classes.push(s.infoBlock__show)

    return (
        <Container>
            <section ref={ref} className={s.infoBlock}>
                <Link to={infoBlockData[0].url}>
                    <div className={classes.join(' ')}>
                        <div className={s.infoBlock__img}>
                            <img src={infoBlockImg1} alt="info block"/>
                        </div>
                        <h2 className={s.infoBlock__title}>{infoBlockData[0].title}</h2>
                        <div className={s.infoBlock__subtitle}>{infoBlockData[0].subtitle}</div>
                    </div>
                </Link>
                <Link to={infoBlockData[1].url}>
                    <div className={classes.join(' ')}>
                        <div className={s.infoBlock__img}>
                            <img src={infoBlockImg2} alt="info block2"/>
                        </div>
                        <h2 className={s.infoBlock__title}>{infoBlockData[1].title}</h2>
                        <div className={s.infoBlock__subtitle}>{infoBlockData[1].subtitle}</div>
                    </div>
                </Link>
                <Link to={infoBlockData[2].url}>
                    <div className={classes.join(' ')}>
                        <div className={s.infoBlock__img}>
                            <img src={infoBlockImg3} alt="info block3"/>
                        </div>
                        <h2 className={s.infoBlock__title}>{infoBlockData[2].title}</h2>
                        <div className={s.infoBlock__subtitle}>{infoBlockData[2].subtitle}</div>
                    </div>
                </Link>
            </section>
        </Container>
    );
};

export default InfoBlock;
