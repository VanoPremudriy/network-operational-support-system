import React, { Component } from 'react';
import styles from "Frontend/components/CustomButton/CustomButtonNonColor.module.css"

interface CustomButtonProps {
  label: string;
  onClick: () => void;
}

const CustomButtonNonColor: React.FC<CustomButtonProps> = ({label, onClick}) => {

  return (
    <button onClick={onClick} className={styles.button}>
      {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default CustomButtonNonColor;