import React from 'react';
import styles from "Frontend/components/CustomInput/CustomInput.module.css"

interface CustomInputProps {
  name: string,
  inputName: string
}

const CustomInput: React.FC<CustomInputProps> = ({name, inputName}) => {
  return (
    <div className={styles.div}>
      <h5>{name}</h5>
      <input type={'text'} name={inputName.toString()} className={styles.input}/>
    </div>
  )
}

export default CustomInput;