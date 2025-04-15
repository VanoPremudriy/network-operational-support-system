import { useState } from "react";
import styles from "./SelectButton.module.css";

const SelectButton = () => {
  const [selected, setSelected] = useState("Выберите");
  const [isOpen, setIsOpen] = useState(false);

  const options = [
    "Стилизация select CSS",
    "Стилизация select JavaScript",
    "Стилизация select, используя input",
  ];

  const handleSelect = (option: string) => {
    setSelected(option);
    setIsOpen(true);
  };

  const selectButtonName: String = "Название"

  return (

    <div className={styles.selectButton}>
      <div>{selectButtonName}</div>
      <div
        className={styles.selectHead}
        onClick={() => setIsOpen(!isOpen)}
      >
        {selected}
        <span className={`${styles.arrow} ${isOpen ? styles.open : ""}`} />
      </div>

      {isOpen && (
        <ul className={styles.selectList}>
          {options.map((option, index) => (
            <li key={index} className={styles.selectItem} onClick={() => handleSelect(option)}>
              {option}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SelectButton;

