import React from 'react'
import Container from '../../components/UI/Container'
import s from './style.module.css'
import Button from '../../components/UI/Button'
import CategoryList from '../../components/CategoryList'

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
    </div>
  )
}
