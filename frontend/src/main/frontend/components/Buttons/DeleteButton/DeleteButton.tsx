import React, { Component } from 'react';
import styles from "./DeleteButton.module.css"

interface DeleteButtonProps {
  label: string;
  onClick: () => void;
}

const DeleteButton: React.FC<DeleteButtonProps> = ({label, onClick}) => {

  return (
    <button type="submit" className={styles.button} onClick={onClick}>
      {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default DeleteButton;