import React, { useState } from 'react';
import useTasks, { useRejectTask } from 'Frontend/services/TaskService';
import styles from './TaskList.module.css';
import TaskRow from 'Frontend/components/Task/TaskRow/TaskRow';
import TaskRoutesModal from 'Frontend/components/Task/TaskRoutesModal/TaskRoutesModal';
import { Task } from 'Frontend/types/Task';
import { useNotification } from 'Frontend/components/Notifications/NotificationContext';

const TaskList = () => {
  const [page, setPage] = useState(0);
  const { tasks, numberOfPages, loading, error, refetch } = useTasks({ page });


  const [selectedTask, setSelectedTask] = useState<Task | null>(null);

  const rejectTask = useRejectTask();

  const { showNotification } = useNotification();

  const handleRejectTask = async (taskId: string) => {
    try {
      const response = await rejectTask(taskId);
      if (response.success) {
        showNotification(
          'success',
          'Маршруты отклонены.',
          'Маршруты задачи отклонены.'
        );

        await refetch();
      } else if (response?.error) {
        const title = response.error.title || 'Ошибка отклонения маршрута';
        let message = 'Произошла неизвестная ошибка';

        if (response.error.infos) {
          const infosValues = Object.values(response.error.infos);
          if (infosValues.length > 0 && typeof infosValues[0] === 'string') {
            message = infosValues[0];
          }
        }

        showNotification('error', title, message);
      } else {
        showNotification(
          'error',
          'Ошибка',
          'Не удалось отклонить маршруты: неизвестный ответ от сервера'
        );
      }
    } catch (e) {
      showNotification('error', 'Сетевая ошибка', 'Не удалось связаться с сервером');
    }
  };


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
              onShowDetails={setSelectedTask}
              onRejectTask={() => handleRejectTask(task.id || '')}
            />
          ))}
          </tbody>
        </table>

        {selectedTask && (
          <TaskRoutesModal
            task={selectedTask}
            onClose={() => setSelectedTask(null)}
            onRouteApplied={refetch} // ✅ передаём функцию обновления
          />
        )}
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