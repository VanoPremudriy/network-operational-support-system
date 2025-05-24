import React, { useState } from 'react';
import styles from './TaskRoutesModal.module.css';
import { Task } from 'Frontend/types/Task';
import { getTaskRoutes, useApplyRoute } from 'Frontend/services/RouteService';
import MapModal from 'Frontend/components/Task/MapModal/MapModal';
import ApplyRouteConfirmation from 'Frontend/components/Task/ApplyRouteConfirmation/ApplyRouteConfirmation';
import { useNotification } from 'Frontend/components/Notifications/NotificationContext';

interface TaskRoutesModalProps {
  task: Task;
  onClose: () => void;
  onRouteApplied?: () => void;
}

const TaskRoutesModal: React.FC<TaskRoutesModalProps> = ({ task, onClose, onRouteApplied }) => {
  const [page, setPage] = useState(0);
  const { routes, numberOfPages, loading, error } = getTaskRoutes({ taskId: task.id!, page });
  const [mapRouteId, setMapRouteId] = useState<string | null>(null);
  const [selectedRouteIdForApply, setSelectedRouteIdForApply] = useState<string | null>(null);

  const applyRoute = useApplyRoute();

  const { showNotification } = useNotification();

  const handleApplyRoute = async () => {
    if (!selectedRouteIdForApply) return;

    try {
      const response = await applyRoute(selectedRouteIdForApply);

      if (response?.success) {
        showNotification(
          'success',
          'Маршрут применён',
          'Выбранный маршрут успешно сохранён и применён'
        );
        setSelectedRouteIdForApply(null);
        onClose(); // закрываем модалку после применения
        onRouteApplied?.();
      } else if (response?.error) {
        const title = response.error.title || 'Ошибка применения маршрута';
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
          'Не удалось применить маршрут: неизвестный ответ от сервера'
        );
      }
    } catch (e) {
      showNotification('error', 'Сетевая ошибка', 'Не удалось связаться с сервером');
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>Маршруты для задачи {task.id}</h2>

        <button className={styles.closeButton} onClick={onClose}>×</button>

        {loading && <p>Загрузка маршрутов...</p>}
        {error && <p className={styles.error}>{error}</p>}

        {!loading && !error && (
          <>
            <table className={styles.table}>
              <thead>
              <tr>
                <th>Номер</th>
                <th>Точка начала</th>
                <th>Точка назначения</th>
                <th>Скорость</th>
                <th>Количество переключений</th>
                <th>Стоимость маршрута</th>
                <th>Расстояние</th>
                <th>Доступные действия</th>
              </tr>
              </thead>
              <tbody>
              {routes.map((route) => (
                <tr key={route.id}>
                  <td>{route.id}</td>
                  <td>{route.startingPoint}</td>
                  <td>{route.destinationPoint}</td>
                  <td>{route.capacity}</td>
                  <td>{route.shifts}</td>
                  <td>{route.price}</td>
                  <td>{route.distance}</td>
                  <td>
                    <div className={styles.actions}>
                      <button
                        className={styles.actionBtn}
                        onClick={() => setMapRouteId(route.id!)}
                      >
                        Отобразить на карте
                      </button>
                      <button
                        className={`${styles.primaryBtn} ${task.status === 'SUCCESS' ? styles.primaryBtnDisabled : ''}`}
                        onClick={() => setSelectedRouteIdForApply(route.id!)}
                        disabled={task.status === 'SUCCESS'}
                      >
                        Применить
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
              </tbody>
            </table>

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

              {mapRouteId && (
                <MapModal routeId={mapRouteId} onClose={() => setMapRouteId(null)} />
              )}

              {selectedRouteIdForApply && (
                <ApplyRouteConfirmation
                  isOpen={true}
                  onConfirm={handleApplyRoute}
                  onClose={() => setSelectedRouteIdForApply(null)}
                />
              )}
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default TaskRoutesModal;
