import React, { useState } from 'react';
import styles from './ClientUpdateForm.module.css';
import { Client } from '../ClientRow/ClientRow';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';
import CancelButton from 'Frontend/components/Buttons/CancelButton/CancelButton';

interface Props {
  client: Client;
  onClose: () => void;
  onSave: (updatedClient: Client) => void;
}

const ClientUpdateForm: React.FC<Props> = ({ client, onClose, onSave }) => {
  const [formData, setFormData] = useState<Client>({ ...client });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = () => {
    onSave(formData);
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2>Обновление клиентских данных</h2>
        <label>Имя</label>
        <input name="firstName" value={formData.firstName} onChange={handleChange} />
        <label>Фамилия</label>
        <input name="lastName" value={formData.lastName} onChange={handleChange} />
        <label>Отчество</label>
        <input name="middleName" value={(formData as any).middleName || ''} onChange={handleChange} />
        <label>E-mail</label>
        <input name="email" value={formData.email} onChange={handleChange} />
        <div className={styles.buttons}>
          <CustomButtonColor label={"Обновить данные"} onClick={handleSubmit}/>
          <CancelButton label={"Отменить"} onClick={onClose}/>
        </div>
      </div>
    </div>
  );
};

export default ClientUpdateForm;

