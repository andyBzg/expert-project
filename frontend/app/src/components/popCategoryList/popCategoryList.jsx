import React from 'react';
import {useSelector} from "react-redux";
import Container from "../UI/Container";
import s from "./style.module.css";

import PopCategoryItem from "../popCategoryItem/popCategoryItem";

const PopCategoryList = () => {
    const categories = useSelector(state => state.categories.list)
    return (
        <div>
            <Container>
                <h2 className={s.popCategories__title}>Задай питання експерту</h2>
                <div className={s.popCategories__list}>
                    {
                        categories.map(el => <PopCategoryItem {...el} key={el.categoryId}/>)
                    }
                </div>
            </Container>
        </div>
    );
};

export default PopCategoryList;
