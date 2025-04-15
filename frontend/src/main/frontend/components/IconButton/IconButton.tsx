import React from 'react';
import styles from 'Frontend/components/IconButton/IconButton.module.css';

interface IconButtonProps {
  onClick: () => void;
  icon? : React.ReactNode;
}

const IconButton: React.FC<IconButtonProps> = ({onClick, icon}) => {

  return (
    <button onClick={onClick} className={styles.button}>
      {icon && <span className={styles.icon}>{icon}</span>}
    </button>
  )
}

export default IconButton;