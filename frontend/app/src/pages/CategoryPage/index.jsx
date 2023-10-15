import s from './style.module.css'
import Container from '../../components/UI/Container';
import ExpertList from '../../components/ExpertList';
const CategoryPage = () => {
    return (
        <Container>
            <div className={s.page}>
                CategoryPage
                <ExpertList/>
            </div>
        </Container>
    );
};

export default CategoryPage;