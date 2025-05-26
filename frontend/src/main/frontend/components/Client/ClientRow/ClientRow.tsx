import React from 'react';
import styles from './ClientRow.module.css';
import DeleteButton from 'Frontend/components/Buttons/DeleteButton/DeleteButton';
import UpdateButton from 'Frontend/components/Buttons/UpdateButton/UpdateButton';
import { Client } from 'Frontend/types/Client';

interface ClientRowProps {
  client: Client;
  onUpdate: (client: Client) => void;
  onDelete: (id: string) => void;
}

const ClientRow: React.FC<ClientRowProps> = ({ client, onUpdate, onDelete }) => {
  return (
    <tr className={styles.row}>
      <td className={styles.cell}>{client.organization}</td>
      <td className={styles.cell}>{client.firstName}</td>
      <td className={styles.cell}>{client.lastName}</td>
      <td className={styles.cell}>{client.email}</td>
      <td className={`${styles.cell} ${styles.actions}`}>
        <UpdateButton label={"Обновить данные"} onClick={() => onUpdate(client)}/>
        <DeleteButton label={"Удалить клиента"} onClick={() => onDelete(client.id!)}/>
      </td>
    </tr>
  );
};

export default ClientRow;
