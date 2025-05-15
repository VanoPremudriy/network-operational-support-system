import React, { Component } from 'react';
import styles from "./CancelButton.module.css"

interface CancelButtonProps {
  label: string;
  onClick: () => void;
}

const CancelButton: React.FC<CancelButtonProps> = ({label, onClick}) => {

  return (
    <button type="submit" className={styles.button} onClick={onClick}>
      {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default CancelButton;