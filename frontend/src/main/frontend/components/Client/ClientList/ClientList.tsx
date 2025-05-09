import React, { useState } from 'react';
import ClientRow, { Client } from '../ClientRow/ClientRow';
import styles from "./ClientList.module.css"

interface ClientsListProps {
  onUpdate: (client: Client) => void;
}

const ClientsList: React.FC<ClientsListProps> = ({ onUpdate }) => {
  const [clients, setClients] = useState<Client[]>([
    {
      login: '1231231',
      firstName: 'Alexander',
      lastName: 'Савоськин',
      email: 'contact@.ry',
    },
    {
      login: '1231231',
      firstName: 'Alexander',
      lastName: 'Tato',
      email: 'contact@bc.cm',
    },
  ]);

  const handleUpdate = (client: Client) => {
    console.log('Обновить:', client);
  };

  const handleDelete = (client: Client) => {
    setClients((prev) => prev.filter((c) => c !== client));
  };

  return (
    <div className={styles.tableWrapper}>
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
        {clients.map((client, index) => (
          <ClientRow
            key={index}
            client={client}
            onUpdate={onUpdate}
            onDelete={handleDelete}
          />
        ))}
        </tbody>
      </table>
    </div>
  );
};

export default ClientsList;