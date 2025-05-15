import React, { useState } from 'react';
import ClientRow from '../ClientRow/ClientRow';
import { Client } from 'Frontend/types/Client'
import styles from "./ClientList.module.css";
import useClients, { deleteClient } from 'Frontend/services/ClientService';

interface ClientsListProps {
  onUpdate: (client: Client) => void;
  refreshTrigger: number;
  setRefreshTrigger: React.Dispatch<React.SetStateAction<number>>;
}

const ClientsList: React.FC<ClientsListProps> = ({ onUpdate, refreshTrigger, setRefreshTrigger }) => {
  const [page, setPage] = useState(0);
  const { clients, numberOfPages, loading, error } = useClients({ page, refreshTrigger });

  const handleDelete = async (id?: string) => {
    if (!id) return;

    const res = await deleteClient(id);
    if (res.success) {
      setRefreshTrigger(Date.now());
    } else {
      alert(res.error?.title || 'Ошибка удаления');
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
            <th>Логин</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>E-mail</th>
            <th>Доступные действия</th>
          </tr>
          </thead>
          <tbody>
          {clients.map((client) => (
            <ClientRow
              key={client.id}
              client={client}
              onUpdate={onUpdate}
              onDelete={handleDelete}
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
        </button>

      </div>
    </div>
  );
};

export default ClientsList;
