import React, { useState } from 'react';
import styles from './ClientUpdateForm.module.css';
import { Client } from 'Frontend/types/Client';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';
import CancelButton from 'Frontend/components/Buttons/CancelButton/CancelButton';
import { createClient, updateClient } from 'Frontend/services/ClientService';

interface Props {
  client?: Client;
  mode: 'create' | 'edit';
  onClose: () => void;
  onSave: (updatedClient: Client) => void;
}

const ClientUpdateForm: React.FC<Props> = ({ client, mode, onClose, onSave }) => {
  const [formData, setFormData] = useState<Client>(() =>
    client ?? { id: '', login: '', firstName: '', lastName: '', middleName: '', email: '' }
  );

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    const response =
      mode === 'edit'
        ? await updateClient({updatedClient: formData })
        : await createClient({newClient: formData });

    if (response.success) {
      setErrors({});
      onSave(formData);
      onClose();
    } else {
      setErrors(response.errors ?? {});
      if (!response.errors) {
        alert('Ошибка сохранения');
      }
    }
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2>{mode === 'edit' ? 'Редактирование клиента' : 'Создание нового клиента'}</h2>
        {mode === 'create' && (
          <>
            <label>Логин</label>
            <input name="login" value={formData.login} onChange={handleChange} />
            <div className={`${styles.error} ${errors.login || errors.text ? styles.visible : ''}`}>
              {errors.login || errors.text || ''}
            </div>
          </>
        )}

        <label>Имя</label>
        <input name="firstName" value={formData.firstName} onChange={handleChange} />
        <div className={`${styles.error} ${errors.firstName ? styles.visible : ''}`}>
          {errors.firstName || ''}
        </div>

        <label>Фамилия</label>
        <input name="lastName" value={formData.lastName} onChange={handleChange} />
        <div className={`${styles.error} ${errors.lastName ? styles.visible : ''}`}>
          {errors.lastName || ''}
        </div>

        <label>Отчество</label>
        <input name="middleName" value={formData.middleName ?? ''} onChange={handleChange} />
        <div className={`${styles.error} ${errors.middleName ? styles.visible : ''}`}>
          {errors.middleName || ''}
        </div>

        <label>E-mail</label>
        <input name="email" value={formData.email} onChange={handleChange} />
        <div className={`${styles.error} ${errors.email ? styles.visible : ''}`}>
          {errors.email || ''}
        </div>

        <div className={styles.buttons}>
          <CustomButtonColor label={mode === 'edit' ? "Обновить данные" : "Создать клиента"} onClick={handleSubmit} />
          <CancelButton label={"Отменить"} onClick={onClose} />
        </div>
      </div>
    </div>
  );
};

export default ClientUpdateForm;