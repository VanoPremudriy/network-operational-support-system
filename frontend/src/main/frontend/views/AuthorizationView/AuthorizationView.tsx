import Authorization from 'Frontend/components/Authorization/Authorization';
import styles from "./AuthorizationView.module.css"
import Header from 'Frontend/components/Header/Header';

const AuthorizationView = () => {
  return (
    <body className={styles.body}>
      <div className={styles.div}>
        <Authorization/>
      </div>
    </body>
  )
}

export default AuthorizationView;