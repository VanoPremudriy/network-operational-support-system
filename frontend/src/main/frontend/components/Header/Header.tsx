import React, { useEffect } from 'react';
import styles from './Header.module.css'
import Logo from 'Frontend/components/Header/Logo/Logo';
import CustomButtonWithIcon from 'Frontend/components/CustomButton/CustomButtonWithIcon';
import {Users, Box, LineChart} from "lucide-react"
import { LuChartColumnBig } from "react-icons/lu";
import { RiSettings4Fill } from "react-icons/ri";
import IconButton from 'Frontend/components/IconButton/IconButton';
import { IoIosNotifications } from "react-icons/io";
import { useNavigate } from 'react-router-dom';
import { RoutePath } from 'Frontend/routes';

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

 return (
   <div className={styles.menu}>
     <CustomButtonWithIcon label={"Клиенты"} onClick={async () => {}} icon={<Users size={20}/>} />
     <CustomButtonWithIcon label={"Построение маршрута"} onClick={() => {}} icon={<LineChart size={20}/>} />
     <CustomButtonWithIcon label={"Оборудование"} onClick={() => {}} icon={<Box size={20}/>} />
     <CustomButtonWithIcon label={"Отчеты"} onClick={() => {}} icon={<LuChartColumnBig size={20}/>}/>
   </div>
 )
}

const userMenu = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.userMenu}>
      <IconButton onClick={() => {
        localStorage.removeItem("token");
        navigate(RoutePath.AUTH)
      }} icon={<RiSettings4Fill size={25}/>}/>
      <IconButton onClick={() => {}} icon={<IoIosNotifications size={25}/>}/>
    </div>
  )
}

export default Header;

