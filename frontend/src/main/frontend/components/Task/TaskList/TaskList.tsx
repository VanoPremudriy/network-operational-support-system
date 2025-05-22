import React, { useState } from 'react';
import useTasks from 'Frontend/services/TaskService';
import styles from './TaskList.module.css';
import TaskRow from 'Frontend/components/Task/TaskRow/TaskRow';

const TaskList = () => {
  const [page, setPage] = useState(0);
  const {tasks, numberOfPages, loading, error} = useTasks({page});


  return (
    <div className={styles.tableWrapper}>
      <div className={styles.tableContainer}>
        {loading && <p>Загрузка...</p>}
        {error && <p className={styles.error}>{error}</p>}
        <table className={styles.table}>
          <thead>
          <tr>
            <th>Номер</th>
            <th>Клиент</th>
            <th>Дата создания</th>
            <th>Статус</th>
            <th>Доступные действия</th>
          </tr>
          </thead>
          <tbody>
          {tasks.map((task) => (
            <TaskRow
              key={task.id}
              task={task}
              onShowDetails={() => {}}
            />
          ))}
          </tbody>
        </table>
      </div>

      <div className={styles.pagination}>
        <button
          onClick={() => setPage((prev) => Math.max(0, prev - 1))}
          disabled={page === 0}
          className={styles.pageButton}
        >
          ◀
        </button>

        {Array.from({ length: numberOfPages }).map((_, i) => (
          <button
            key={i}
            onClick={() => setPage(i)}
            className={`${styles.pageButton} ${i === page ? styles.active : ''}`}
          >
            {i + 1}
          </button>
        ))}

        <button
          onClick={() => setPage((prev) => Math.min(numberOfPages - 1, prev + 1))}
          disabled={page === numberOfPages - 1}
          className={styles.pageButton}
        >
          ▶
        </button>

      </div>
    </div>

  );
};

export default TaskList;