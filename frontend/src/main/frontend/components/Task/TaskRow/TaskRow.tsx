import React from 'react';
import styles from './TaskRow.module.css';
import UpdateButton from 'Frontend/components/Buttons/UpdateButton/UpdateButton';
import { Task } from 'Frontend/types/Task';
import StatusBadge, { StatusType } from 'Frontend/components/Task/StatusBadge/StatusBadge';
import { formatDateTime } from 'Frontend/hooks/formatDateTime';
import DeleteButton from 'Frontend/components/Buttons/DeleteButton/DeleteButton';

interface TaskRowProps {
  task: Task;
  onShowDetails: (task: Task) => void;
  onRejectTask: (task: Task) => void;
}

const TaskRow: React.FC<TaskRowProps> = ({ task, onShowDetails, onRejectTask }) => {

  const routeAlgorithmLabels: Record<string, string> = {
    routeSearchMaxflow: 'Максимальный поток',
    routeSearch: "Поиск маршрута",
  };

  const getAlgorithmLabel = (key?: string): string => {
    if (!key) return '—';
    return routeAlgorithmLabels[key] ?? `Неизвестный алгоритм (${key})`;
  };

  return (
    <tr className={styles.row}>
      <td className={styles.cell}>{task.id}</td>
      <td className={styles.cell}>{task.clientName}</td>
      <td className={styles.cell}>{formatDateTime(task.createdTime || '')}</td>
      <td className={styles.cell}>{getAlgorithmLabel(task.routeBuildingAlgorithm)}</td>
      <td className={styles.cell}><StatusBadge status={task.status as StatusType || 'FAILED'} /></td>
      <td className={`${styles.cell} ${styles.actions}`}>
        {(task.status === 'SELECTION_IS_REQUIRED' || task.status === "SUCCESS") && (
          <UpdateButton label="Посмотреть детали" onClick={() => onShowDetails(task)} />
        )}
        {(task.status === 'SELECTION_IS_REQUIRED') && (
          <DeleteButton label={"Отклонить маршруты"} onClick={() => onRejectTask(task)} />
        )}
      </td>
    </tr>
  );
};

export default TaskRow;