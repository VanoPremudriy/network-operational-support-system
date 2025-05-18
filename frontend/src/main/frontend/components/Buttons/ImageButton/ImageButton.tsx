import React from 'react';
import styles from 'Frontend/components/Buttons/ImageButton/ImageButton.module.css';

interface ImageButtonProps {
  onClick: () => void;
  src: string;
  alt?: string;
  size?: number; // опционально: задать размер изображения
}

const ImageButton: React.FC<ImageButtonProps> = ({ onClick, src, alt = '', size = 24 }) => {
  return (
    <button onClick={onClick} className={styles.button}>
      <img src={src} alt={alt} width={size} height={size} className={styles.image} />
    </button>
  );
};

export default ImageButton;