import React from 'react';
import styles from './StatusBadge.module.css';

export type StatusType =
  | 'SUCCESS'
  | 'FAILED'
  | 'IN_PROGRESS'
  | 'SELECTION_IS_REQUIRED'
  | 'ROUTE_DECLINED';

interface StatusBadgeProps {
  status: StatusType;
}

const statusMap: Record<StatusType, { text: string; className: string }> = {
  SUCCESS: { text: 'Success', className: styles.success },
  FAILED: { text: 'Failed', className: styles.failed },
  IN_PROGRESS: { text: 'IN PROGRESS', className: styles.inProgress },
  SELECTION_IS_REQUIRED: { text: 'SELECTION\nIS REQUIRED', className: styles.selectionRequired },
  ROUTE_DECLINED: { text: 'ROUTE\nDECLINED', className: styles.routeDeclined },
};

const StatusBadge: React.FC<StatusBadgeProps> = ({ status }) => {
  const { text, className } = statusMap[status];

  return (
    <div className={`${styles.badge} ${className}`}>
      {text.split('\n').map((line, i) => (
        <div key={i}>{line}</div>
      ))}
    </div>
  );
};

export default StatusBadge;