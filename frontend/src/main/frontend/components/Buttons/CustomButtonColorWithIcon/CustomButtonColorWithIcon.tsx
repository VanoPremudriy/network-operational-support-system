import React from 'react';
import styles from "Frontend/components/Buttons/CustomButtonColorWithIcon/CustomButtonColorWithIcon.module.css"

interface CustomButtonProps {
  label: string;
  onClick: () => void;
  icon?: React.ReactNode;
  iconPosition?: 'left' | 'right';  // Проп для выбора положения иконки
}

const CustomButtonColorWithIcon: React.FC<CustomButtonProps> = ({label, onClick, icon, iconPosition = 'left'}) => {
  return (
    <button onClick={onClick} className={styles.button}>
      {icon && iconPosition === 'left' && <span className={styles.icon}>{icon}</span>}
      {label && <span className={styles.label}>{label}</span>}
      {icon && iconPosition === 'right' && <span className={styles.icon}>{icon}</span>}
    </button>
  );
};

export default CustomButtonColorWithIcon;
