import React, { useState } from "react";
import styles from "./CustomInput.module.css";
import { Eye, EyeOff } from "lucide-react";

interface CustomInputProps {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  type?: "text" | "password";
}

const CustomInput: React.FC<CustomInputProps> = ({
    label,
    name,
    value,
    onChange,
    type = "text",
  }) => {
  const [showPassword, setShowPassword] = useState(false);
  const isPassword = type === "password";

  return (
    <div className={styles.div}>
      <h5>{label}</h5>
      <div className={styles.inputWrapper}>
        <input
          type={isPassword && showPassword ? "text" : type}
          name={name}
          className={styles.input}
          value={value}
          onChange={onChange}
        />
        {isPassword && (
          <button type="button" onClick={() => setShowPassword((prev) => !prev)} className={styles.toggleButton}>
            {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
          </button>
        )}
      </div>
    </div>
  );
};

export default CustomInput;