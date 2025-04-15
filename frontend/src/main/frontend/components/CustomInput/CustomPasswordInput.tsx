import { useState } from 'react';
import styles from 'Frontend/components/CustomInput/CustomPasswordInput.module.css';
import { Eye, EyeOff } from 'lucide-react';

const CustomPasswordInput = () => {
  const [showPassword, setShowPassword] = useState(false);

  return (
    <div className={styles.passwordDiv}>
      <h5>Пароль</h5>
      <div className={styles.inputWrapper}>
        <input
          type={showPassword ? "text" : "password"}
          name="password"
          className={styles.passwordInput}
        />
        <button
          type="button"
          onClick={() => setShowPassword((prev) => !prev)}
          className={styles.toggleButton}
        >
          {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
        </button>
      </div>
    </div>
  );
}

export default CustomPasswordInput;