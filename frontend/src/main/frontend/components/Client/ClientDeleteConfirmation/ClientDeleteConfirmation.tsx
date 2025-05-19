import React from 'react';
import styles from './ClientDeleteConfirmation.module.css';
import CancelButton from 'Frontend/components/Buttons/CancelButton/CancelButton';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';

interface DeleteClientConfirmationProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

const DeleteClientConfirmation: React.FC<DeleteClientConfirmationProps> = (
  {
    isOpen,
    onClose,
    onConfirm,
  }) => {
  if (!isOpen) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <button className={styles.closeButton} onClick={onClose}>×</button>
        <h2>Вы хотите удалить клиента?</h2>
        <p>После удаления клиента все его маршруты будут удалены</p>
        <div className={styles.buttons}>
          <CancelButton onClick={onClose} label={"Отменить"}/>
          <CustomButtonColor label={ "Подтвердить"} onClick={ onConfirm }/>
        </div>
      </div>
    </div>
  );
};

export default DeleteClientConfirmation;