import React from 'react';
import {useSelector} from "react-redux";
import Container from "../UI/Container";
import s from "./style.module.css";

import PopCategoryItem from "../popCategoryItem/popCategoryItem";

const PopCategoryList = ({popCategoriesColumn}) => {

    let classes = [s.popCategories__list]
    if (popCategoriesColumn === 2)
        classes = [...classes, s.popCategories__column2]
    if (popCategoriesColumn === 3)
        classes = [...classes, s.popCategories__column3]

    const categories = useSelector(state => state.categories.list)
    return (
        <div>
            <Container className={s.container}>
                <h2 className={s.popCategories__title}>Задай питання експерту</h2>
                <div className={classes.join(' ')}>
                    {
                        categories.map(el => <PopCategoryItem {...el} key={el.categoryId}/>)
                    }
                </div>
            </Container>
        </div>
    );
};

export default PopCategoryList;
