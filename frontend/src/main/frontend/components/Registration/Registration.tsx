import styles from "Frontend/components/Registration/Registration.module.css"
import CustomInput from 'Frontend/components/CustomInput/CustomInput';
import CustomButtonColor from 'Frontend/components/CustomButton/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/CustomButton/CustomButtonNonColor';
import { useNavigate } from 'react-router-dom';
import { RoutePath } from 'Frontend/routes';
import React, { useState } from 'react';
import { getAuthToken, registration } from 'Frontend/services/AuthorizationService';

const Registration = () => {
  const navigate = useNavigate();
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const [formData, setFormData] = useState({
    login: "",
    password: "",
    firstName: "",
    lastName: ""
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const { success, errors } = await registration(formData);
    if (success) {
      navigate(RoutePath.ROOT);
    } else {
      setErrors(errors || {});
    }
  };

  return (
    <div className={styles.div}>
      <h1 className={styles.h1}>Регистрация</h1>
      <form className={styles.form} onSubmit={handleSubmit}>
        <CustomInput label={'Логин'} name={'login'} value={formData.login} onChange={handleChange} />
        <div className={`${styles.error} ${errors.login ? styles.visible : ''}`}>
          {errors.login || ''}
        </div>

        <CustomInput label={'Пароль'} name={'password'} value={formData.password} onChange={handleChange}
                     type={"password"} />
        <div className={`${styles.error} ${errors.password ? styles.visible : ''}`}>
          {errors.password || ''}
        </div>

        <CustomInput label={'Имя сотрудника'} name={'firstName'} value={formData.firstName} onChange={handleChange} />
        <div className={`${styles.error} ${errors.firstName ? styles.visible : ''}`}>
          {errors.firstName || ''}
        </div>

        <CustomInput label={'Фамилия сотрудника'} name={'lastName'} value={formData.lastName} onChange={handleChange} />
        <div className={`${styles.error} ${errors.lastName ? styles.visible : ''}`}>
          {errors.lastName || ''}
        </div>


        <div className={styles.buttons}>
          <CustomButtonColor label={"Регистрация"} onClick={() => {
          }} />
          <CustomButtonNonColor label={"Авторизация"} onClick={() => {
            navigate(RoutePath.AUTH)
          }} />
        </div>
      </form>
    </div>
  )
}

export default Registration;