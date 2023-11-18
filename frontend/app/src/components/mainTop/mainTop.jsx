import React from 'react';
import s from './style.module.css'
import Container from "../UI/Container";
import decor from './../../media/main-top-decor.png'
import decor2 from './../../media/main-top-decor2.png'
import airplane from './../../media/airplane.png'

const MainTop = () => {
    return (
        <div className={s.mainTop}>
            <Container className={s.container}>
                <div className={s.mainTopDecor}><img src={decor} alt="top decoration"/></div>
                <div className={s.mainTop__plane}><img src={airplane} alt="top airplane"/></div>
                <div className={s.mainTop__center}>
                    <h1 className={s.mainTop__title}>Lorem ipsum dolor</h1>
                    <div className={s.mainTop__search}>
                        <label>
                            <input id={`search`} className={s.inputSearch}
                                   placeholder={`Є питання? Задай питання експерту`} type="search"
                                   name={`search`}/>
                            <div className={s.btnSearch}><i className="las la-search"></i></div>
                        </label>
                    </div>
                    <h3 className={s.mainTop__subtitle}>Lorem ipsum dolor.</h3>
                </div>
                <div className={s.mainTopDecor2}><img src={decor2} alt="top decoration"/></div>
            </Container>
        </div>
    );
};

export default MainTop;
