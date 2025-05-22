import React, { createContext, useContext, useState, ReactNode } from 'react';
import styles from './NotificationContext.module.css';

type NotificationType = 'success' | 'error';

type Notification = {
  id: number;
  type: NotificationType;
  title: string;
  message: string;
};

type NotificationContextType = {
  showNotification: (type: NotificationType, title: string, message: string) => void;
};

const NotificationContext = createContext<NotificationContextType | undefined>(undefined);

let idCounter = 0;

export const NotificationProvider = ({ children }: { children: ReactNode }) => {
  const [notifications, setNotifications] = useState<Notification[]>([]);

  const showNotification = (type: NotificationType, title: string, message: string) => {
    const id = ++idCounter;
    const newNotification = { id, type, title, message };
    setNotifications((prev) => [...prev, newNotification]);

    setTimeout(() => {
      setNotifications((prev) => prev.filter((n) => n.id !== id));
    }, 5000);
  };

  const removeNotification = (id: number) => {
    setNotifications((prev) => prev.filter((n) => n.id !== id));
  };

  return (
    <NotificationContext.Provider value={{ showNotification }}>
      {children}
      <div className={styles.notificationContainer}>
        {notifications.map(({ id, type, title, message }) => (
          <div
            key={id}
            className={`${styles.notification} ${type === 'success' ? styles.success : styles.error}`}
          >
            <div className={styles.iconBox}>
              {type === 'success' ? '✅' : '❌'}
            </div>
            <div className={styles.textBlock}>
              <div className={styles.title}>{title}</div>
              <div className={styles.message}>{message}</div>
            </div>
            <button className={styles.closeButton} onClick={() => removeNotification(id)}>
              ×
            </button>
          </div>
        ))}
      </div>
    </NotificationContext.Provider>
  );
};

export const useNotification = () => {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error('useNotification must be used within a NotificationProvider');
  }
  return context;
};

