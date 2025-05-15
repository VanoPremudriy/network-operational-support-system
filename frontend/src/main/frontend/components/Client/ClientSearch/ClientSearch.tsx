import React from 'react';
import styles from './ClientSearch.module.css';
import { FaSearch } from 'react-icons/fa';

interface ClientSearchProps {
  query: string;
  onChange: (value: string) => void;
}

const ClientSearch: React.FC<ClientSearchProps> = ({ query, onChange }) => {
  return (
    <div className={styles.searchWrapper}>
      <FaSearch className={styles.icon} />
      <input
        type="text"
        placeholder="Введите ФИО для поиска клиента"
        value={query}
        onChange={(e) => onChange(e.target.value)}
        className={styles.input}
      />
    </div>
  );
};

export default ClientSearch;