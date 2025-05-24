import React from 'react';
import styles from './TaskRow.module.css';
import UpdateButton from 'Frontend/components/Buttons/UpdateButton/UpdateButton';
import { Task } from 'Frontend/types/Task';
import StatusBadge, { StatusType } from 'Frontend/components/Task/StatusBadge/StatusBadge';
import { formatDateTime } from 'Frontend/hooks/formatDateTime';

interface TaskRowProps {
  task: Task;
  onShowDetails: (task: Task) => void;
}

const TaskRow: React.FC<TaskRowProps> = ({ task, onShowDetails }) => {
  return (
    <tr className={styles.row}>
      <td className={styles.cell}>{task.id}</td>
      <td className={styles.cell}>{task.clientName}</td>
      <td className={styles.cell}>{formatDateTime(task.createdTime || '')}</td>
      <td className={styles.cell}><StatusBadge status={task.status as StatusType || 'FAILED'} /></td>
      <td className={`${styles.cell} ${styles.actions}`}>
        {(task.status === 'SELECTION_IS_REQUIRED' || task.status === "SUCCESS") && (
          <UpdateButton label="Посмотреть детали" onClick={() => onShowDetails(task)} />
        )}
      </td>
    </tr>
  );
};

export default TaskRow;