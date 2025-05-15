import React, { Component } from 'react';
import styles from "Frontend/components/Buttons/CustomButtonColor/CustomButtonColor.module.css"

interface CustomButtonProps {
  label: string;
  onClick: () => void;
}

const CustomButtonColor: React.FC<CustomButtonProps> = ({label, onClick}) => {

  return (
    <button type="submit" className={styles.button} onClick={onClick}>
      {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default CustomButtonColor;