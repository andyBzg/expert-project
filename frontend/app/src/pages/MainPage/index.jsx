import React from 'react'
import Container from '../../components/UI/Container'
import s from './style.module.css'
import Button from '../../components/UI/Button'
import CategoryList from '../../components/CategoryList'
import { reasons } from '../../store/content_data/mainPage.js'

export default function MainPage() {
  return (
    <div className={s.page}>
      <Container className={s.poster}>
        <h1 className={s.title}>Есть вопрос? Задай эксперту!</h1>
        <div className={s.buttons}>
          <Button variant={"primary"} size={'size_xl'} uppercase={'uppercase'}>
            задать вопрос
          </Button>
          <Button variant={"outlined"} size={'size_xl'} >
            Заданные вопросы
          </Button>
        </div>
      </Container>
      <CategoryList />
      <Container>
        <div className={s.content}>
          <div className={s.subtitle}>Почему нам доверяют</div>
          <div className={s.reasons_wrapper}>
            {
              reasons.map(({ title, subtitle }) =>
                <div className={s.reason}>
                  <i class="las la-check-square"></i>
                  <div>
                    <h3>{title}</h3>
                    <p>{subtitle}</p>
                  </div>

                </div>)
            }
          </div>
          <Button
            to={'/ask'}
            variant={'primary'}
            uppercase={'uppercase'}
            size={'size_l'}
          >
            получить консультацию
          </Button>
        </div>
      </Container>
    </div>
  )
}
