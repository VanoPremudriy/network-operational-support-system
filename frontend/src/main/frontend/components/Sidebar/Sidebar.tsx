import styles from 'Frontend/components/Sidebar/Sidebar.module.css';
import React, { useState } from 'react';
import SelectButton from 'Frontend/components/SelectButton/SelectButton';
import CustomButtonColor from 'Frontend/components/CustomButton/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/CustomButton/CustomButtonNonColor';


const Sidebar = () => {
  const [isCollapsed, setIsCollapsed] = useState(false);

  const toggleSidebar = () => {
    setIsCollapsed(prev => !prev);
  };

  return (
    <div className={`${styles.sidebar} ${isCollapsed ? styles.collapsed : ''}`}>
      <button className={styles.toggleButton} onClick={toggleSidebar}>
        {isCollapsed ? '⮞' : '⮜'}
      </button>
      {!isCollapsed && (
        <div className={styles.selectBlock}>
          <SelectButton />
          <SelectButton />
          <SelectButton />
          <div className={styles.buttons}>
            <CustomButtonColor label={"Построить"} onClick={() => {}} />
            <CustomButtonNonColor label={"Отменить"} onClick={() => {}} />
          </div>
        </div>
      )}
    </div>
  );
};


export default Sidebar;