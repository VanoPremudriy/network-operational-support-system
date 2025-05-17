import React, { useState, useEffect, useRef } from 'react';
import styles from './SelectButton.module.css';

type OptionType = {
  id: string;
  label: string;
};

type SelectButtonProps = {
  label: string;
  fetchOptions: (query: string) => Promise<OptionType[]>;
  onSelect: (selected: OptionType) => void;
  initialValue?: OptionType;
};

const SelectButton = ({ label, fetchOptions, onSelect, initialValue }: SelectButtonProps) => {
  const [options, setOptions] = useState<OptionType[]>([]);
  const [query, setQuery] = useState(initialValue?.label || '');
  const [selected, setSelected] = useState<OptionType | null>(initialValue || null);
  const [isOpen, setIsOpen] = useState(false);

  const wrapperRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    const loadOptions = async () => {
      try {
        const result = await fetchOptions(query);
        setOptions(result);
      } catch (err) {
        console.error('Ошибка при загрузке опций:', err);
      }
    };

    loadOptions();
  }, [query, fetchOptions]);

  const handleSelect = (option: OptionType) => {
    setSelected(option);
    setQuery(option.label);
    setIsOpen(false);
    onSelect(option);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleBlur = () => {
    const match = options.find((opt) => opt.label === query);
    if (!match) {
      // Введённое значение не совпадает с опцией — сбрасываем
      setQuery(selected?.label || '');
    }
  };

  return (
    <div className={styles.selectButton} ref={wrapperRef}>
      <div>{label}</div>
      <div className={styles.selectHead}>
        <input
          ref={inputRef}
          type="text"
          value={query}
          placeholder="Введите для поиска"
          onChange={(e) => {
            setQuery(e.target.value);
            setIsOpen(true);
          }}
          onFocus={() => setIsOpen(true)}
          onBlur={handleBlur}
          className={styles.input}
          style={{
            border: "none",
            background: "transparent",
            outline: "none",
            width: "100%",
            fontSize: "14px",
            color: "rgba(66, 67, 72, 0.8)",
            cursor: "text"
          }}
        />
        <span
          className={`${styles.arrow} ${isOpen ? styles.open : ''}`}
          onClick={() => setIsOpen((prev) => !prev)}
        />
      </div>

      {isOpen && options.length > 0 && (
        <ul className={styles.selectList}>
          {options.map((option) => (
            <li
              key={option.id}
              className={styles.selectItem}
              onMouseDown={() => handleSelect(option)} // onMouseDown чтобы не терялся фокус
            >
              {option.label}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SelectButton;


// <input
//   ref={inputRef}
//   type="text"
//   value={query}
//   onChange={(e) => setQuery(e.target.value)}
//   placeholder="Введите имя..."
//   onClick={(e) => e.stopPropagation()}
//   onFocus={() => setIsOpen(true)}
//   style={{
//     border: "none",
//     background: "transparent",
//     outline: "none",
//     width: "100%",
//     fontSize: "14px",
//     color: "rgba(66, 67, 72, 0.8)",
//     cursor: "text"
//   }}
// />

// const SelectButton: React.FC<SelectButtonProps> = ({ label, onSelect }) => {
//
//
//   return (
//     <div className={styles.selectButton}>
//       <div>{label}</div>
//       <input
//         type="text"
//         value={inputValue}
//         onClick={() => setIsOpen(true)}
//         onChange={(e) => setInputValue(e.target.value)}
//         className={styles.selectHead}
//         placeholder="Начните ввод..."
//       />
//       {isOpen && (
//         <ul className={styles.selectList}>
//           {options.map((option) => (
//             <li
//               key={option.id}
//               className={styles.selectItem}
//               onClick={() => handleSelect(option)}
//             >
//               {option.fullName}
//             </li>
//           ))}
//         </ul>
//       )}
//     </div>
//   );
// };
//
// export default SelectButton;