import React from 'react';
import styles from './ApplyRouteConfirmation.module.css';
import CancelButton from 'Frontend/components/Buttons/CancelButton/CancelButton';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';

interface ApplyRouteConfirmationProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

const ApplyRouteConfirmation: React.FC<ApplyRouteConfirmationProps> = (
  {
    isOpen,
    onClose,
    onConfirm
  }) => {
  if (!isOpen) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <button className={styles.closeButton} onClick={onClose}>×</button>
        <h2>Вы хотите сохранить вариант маршрута?</h2>
        <p>После сохранения остальные варианты будут удалены.</p>
        <div className={styles.buttons}>
          <CancelButton onClick={onClose} label={"Отменить"} />
          <CustomButtonColor label={"Применить"} onClick={onConfirm} />
        </div>
      </div>
    </div>
  );
};

export default ApplyRouteConfirmation;
