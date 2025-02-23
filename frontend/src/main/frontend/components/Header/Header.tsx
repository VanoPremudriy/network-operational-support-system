import React from "react";
import { Link } from 'react-router-dom';
import styles from './Header.module.css'
import Logo from 'Frontend/components/Header/Logo/Logo';
import CustomButton from 'Frontend/components/CustomButton/CustomButton';
import {Users, Box, LineChart, ChartColumn} from "lucide-react"
import { LuChartColumnBig } from "react-icons/lu";

const Header =() => {
  return (
    <div className={styles.header}>
      <Logo />
      {menu()}
    </div>
)

}

const menu = () => {
 return (
   <div className={styles.menu}>
     <CustomButton label={"Клиенты"} onClick={() => {}} icon={<Users size={20}/>} />
     <CustomButton label={"Построение маршрута"} onClick={() => {}} icon={<LineChart size={20}/>} />
     <CustomButton label={"Оборудование"} onClick={() => {}} icon={<Box size={20}/>} />
     <CustomButton label={"Отчеты"} onClick={() => {}} icon={<LuChartColumnBig size={20}/>}/>
   </div>
 )
}

export default Header;

