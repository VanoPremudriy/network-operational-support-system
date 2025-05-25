import { Task, TaskGetRequest, UseTasksProps } from 'Frontend/types/Task';
import { useCallback, useEffect, useState } from 'react';
import { TaskEndpoint } from 'Frontend/generated/endpoints';
import TaskViewDto from 'Frontend/generated/ru/mirea/cnoss/service/task/dto/TaskViewDto';


const useTasks = ({page}: UseTasksProps) => {
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [tasks, setTasks] = useState<Task[]>([]);
  const [numberOfPages, setNumberOfPages] = useState<number>(0);

  const fetchTasks = useCallback(async () => {
    try {
      setLoading(true);
      const request: TaskGetRequest = {
        currentPage: page
      };

      const data = await TaskEndpoint.getAllTasks(request);

      if (!data.success) {
        setError(data.error?.title || 'Неизвестная ошибка');
        return;
      }

      setTasks((data.tasks ?? []).filter((c): c is TaskViewDto => c !== undefined));
      setNumberOfPages(data.numberOfPages ?? 0);
    } catch (err) {
      setError('Ошибка при загрузке данных');
    } finally {
      setLoading(false);
    }
  }, [page]);

  useEffect(() => {
    fetchTasks();
  }, [fetchTasks]);

  return { tasks, loading, error, numberOfPages, refetch: fetchTasks };
};

export default useTasks;