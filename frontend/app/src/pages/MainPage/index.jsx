import React from 'react'
import Container from '../../components/UI/Container'
import s from './style.module.css'
import Button from '../../components/UI/Button'

export default function MainPage() {
  return (
    <div className={s.page}>
      <div className={s.poster}>
        <Container>
          <div className={s.content_container}>
            <h1 className={s.title}>Есть вопрос? Задай эксперту!</h1>
            <div className={s.buttons}>
              <Button variant={"primary"} size={'size_l'} uppercase={'uppercase'}>
                задать вопрос
              </Button>
              <Button variant={"outlined"} size={'size_l'} >
                Заданные вопросы
              </Button>
            </div>
          </div>
        </Container>
      </div>

    </div>
  )
}
