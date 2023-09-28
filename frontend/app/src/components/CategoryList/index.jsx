import React from 'react';
import Container from '../UI/Container';
import CategoryItem from '../CategoryItem';
import s from './style.module.css'

const CategoryList = () => {
    const data = [
        { categoryId: 1, title: 'Психология', img: '', link: 'personal' },
        { categoryId: 2, title: 'Эзотерика', img: '', link: 'religion' },
        { categoryId: 3, title: 'Юриспруденция', img: '', link: 'law' },
        { categoryId: 4, title: 'Учеба и наука', img: '', link: 'study' },
        { categoryId: 5, title: 'IT Технологии', img: '', link: 'it' },
        { categoryId: 6, title: 'Красота и здоровье', img: '', link: 'beauty' },
        { categoryId: 7, title: 'Бизнес', img: '', link: 'bussines' },
        { categoryId: 8, title: 'Авто', img: '', link: 'autos' },
        { categoryId: 9, title: 'Другое', img: '', link: 'other' },
    ]

    return (
        <Container className={s.container}>
            <div className={s.category_list}>
                {
                    data.map(el => <CategoryItem {...el} key={el.categoryId} />)
                }
            </div>
        </Container>
    );
};

export default CategoryList;