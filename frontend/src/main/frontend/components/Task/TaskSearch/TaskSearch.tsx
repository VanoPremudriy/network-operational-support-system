import React from 'react';
import styles from './TaskSearch.module.css';
import { FaSearch } from 'react-icons/fa';

interface TaskSearchProps {
  query: string;
  onChange: (value: string) => void;
}

const TaskSearch: React.FC<TaskSearchProps> = ({ query, onChange }) => {
  return (
    <div className={styles.searchWrapper}>
      <FaSearch className={styles.icon} />
      <input
        type="text"
        placeholder="Введите номер задачи для поиска"
        value={query}
        onChange={(e) => onChange(e.target.value)}
        className={styles.input}
      />
    </div>
  );
};

export default TaskSearch;