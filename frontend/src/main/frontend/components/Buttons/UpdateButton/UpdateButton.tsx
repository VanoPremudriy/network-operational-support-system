import React, { Component } from 'react';
import styles from "./UpdateButton.module.css"

interface UpdateButtonProps {
  label: string;
  onClick: () => void;
}

const UpdateButton: React.FC<UpdateButtonProps> = ({label, onClick}) => {

  return (
    <button type="submit" className={styles.button} onClick={onClick}>
      {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default UpdateButton;