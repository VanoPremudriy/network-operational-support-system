import styles from "Frontend/components/Registration/Registration.module.css"
import CustomInput from 'Frontend/components/CustomInput/CustomInput';
import CustomButtonColor from 'Frontend/components/CustomButton/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/CustomButton/CustomButtonNonColor';
import { useNavigate } from 'react-router-dom';
import CustomPasswordInput from 'Frontend/components/CustomInput/CustomPasswordInput';
import { RoutePath } from 'Frontend/routes';

const Registration = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.div}>
      <h1 className={styles.h1}>Регистрация</h1>
      <div className={styles.form}>
        <CustomInput name={'Логин'} inputName={'Логин'} />
        <CustomPasswordInput/>
        <CustomInput name={'Имя сотрудника'} inputName={'Имя сотрудника'} />
        <CustomInput name={'Фамилия сотрудника'} inputName={'Фамилия сотрудника'} />
        <div className={styles.buttons}>
        <CustomButtonColor label={"Регистрация"} onClick={() => {}}/>
        <CustomButtonNonColor label={"Авторизация"} onClick={() => {navigate(RoutePath.AUTH)}}/>
        </div>
      </div>
    </div>
  )
}

export default Registration;