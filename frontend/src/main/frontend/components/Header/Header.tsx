import React from 'react';
import styles from './Header.module.css'
import Logo from 'Frontend/components/Header/Logo/Logo';
import CustomButtonWithIcon from 'Frontend/components/Buttons/CustomButtonWithIcon/CustomButtonWithIcon';
import {Users, Box, LineChart} from "lucide-react"
import { RiSettings4Fill } from "react-icons/ri";
import IconButton from 'Frontend/components/Buttons/IconButton/IconButton';
import { useNavigate } from 'react-router-dom';
import { RoutePath } from 'Frontend/routes';
import { RiLogoutBoxLine } from "react-icons/ri";
import { RiFlag2Line } from "react-icons/ri";
import ImageButton from 'Frontend/components/Buttons/ImageButton/ImageButton';
import avatar from '/public/user/avatar.jpg'
import { AuthEndpoint } from 'Frontend/generated/endpoints';

const Header =() => {
  return (
    <div className={styles.header}>
      <Logo />
      {menu()}
      {userMenu()}
    </div>
  )

}

const menu = () => {
  const navigate = useNavigate();


 return (
   <div className={styles.menu}>
     <CustomButtonWithIcon label={"Клиенты"} onClick={() => navigate(RoutePath.CLIENT)} icon={<Users size={20}/>} />
     <CustomButtonWithIcon label={"Построение маршрута"} onClick={() => navigate(RoutePath.MAP)} icon={<LineChart size={20}/>} />
     <CustomButtonWithIcon label={"Задачи"} onClick={() => {navigate(RoutePath.TASK)}} icon={<RiFlag2Line  size={20}/>} />
   </div>
 )
}

const userMenu = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.userMenu}>
      <IconButton onClick={() => {}} icon={<RiSettings4Fill size={25}/>}/>
      <ImageButton
        onClick={() => {}}
        src={avatar}
        alt="Пользователь"
      />
      <IconButton
        onClick={async () => {
          await AuthEndpoint.logout();
          navigate(RoutePath.AUTH);
        }}
        icon={<RiLogoutBoxLine size={25}/>}
      />
    </div>
  )
}

export default Header;