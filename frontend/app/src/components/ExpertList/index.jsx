import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchExperts } from '../../store/slices/expertSlice';
import ExpertCard from '../ExpertCard';


const ExpertList = () => {
  const dispatch = useDispatch();
  const experts = useSelector((state) => state.experts.experts);

  useEffect(() => {
    // Вызываем экшен fetchExperts, чтобы отправить запрос на сервер
    dispatch(fetchExperts());
  }, [dispatch]);

  // Выводите список экспертов, полученный из хранилища, в вашем компоненте
  return (
    <div className="expert-list">
      {experts.map((expert) => (
        <ExpertCard key={expert.id} expert={expert} />
      ))}
    </div>
  );
};

export default ExpertList;