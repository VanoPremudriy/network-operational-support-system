import React, { Component } from 'react';
import styles from "Frontend/components/CustomButton/CustomButtonColor.module.css"

interface CustomButtonProps {
  label: string;
  onClick: () => void;
}

const CustomButtonColor: React.FC<CustomButtonProps> = ({label, onClick}) => {

  return (
    <button onClick={onClick} className={styles.button}>
      {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default CustomButtonColor;