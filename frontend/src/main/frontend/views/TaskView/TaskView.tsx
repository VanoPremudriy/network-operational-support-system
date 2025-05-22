import TaskList from 'Frontend/components/Task/TaskList/TaskList';
import styles from './TaskView.module.css';
import ClientSearch from 'Frontend/components/Client/ClientSearch/ClientSearch';
import TaskSearch from 'Frontend/components/Task/TaskSearch/TaskSearch';
import { useState } from 'react';


const TaskView = () => {
  const [searchQuery, setSearchQuery] = useState('');

  return (
    <div className={styles.mainContainer}>
      <div className={styles.container}>
        <div className={styles.header}>
          <h1 className={styles.title}>Задачи</h1>
        </div>

        <div className={styles.list}>
          <TaskSearch query={searchQuery} onChange={setSearchQuery} />
          <TaskList/>

        </div>

      </div>
      {/*{isFormOpen && (*/}
      {/*  <ClientUpdateForm*/}
      {/*    client={selectedClient ?? undefined}*/}
      {/*    mode={formMode}*/}
      {/*    onClose={() => {*/}
      {/*      setIsFormOpen(false);*/}
      {/*      setSelectedClient(null);*/}
      {/*    }}*/}
      {/*    onSave={() => {*/}
      {/*      setIsFormOpen(false);*/}
      {/*      setSelectedClient(null);*/}
      {/*      setRefreshTrigger(Date.now());*/}
      {/*    }}*/}
      {/*  />*/}
      {/*)}*/}
    </div>
  );
}

export default TaskView;