import React, { Component } from 'react';
import styles from "Frontend/components/Buttons/CustomButtonWithIcon/CustomButtonWithIcon.module.css"

interface CustomButtonProps {
  label: string;
  onClick: () => void;
  icon? : React.ReactNode;
}

const CustomButtonWithIcon: React.FC<CustomButtonProps> = ({label, onClick, icon}) => {

  return (
    <button onClick={onClick} className={styles.button}>
        {icon && <span className={styles.icon}>{icon}</span>}
        {label && <span className={styles.label}>{label}</span>}
    </button>
  )
}

export default CustomButtonWithIcon;