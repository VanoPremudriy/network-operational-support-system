import Header from 'Frontend/components/Header/Header';
import Sidebar from 'Frontend/components/Sidebar/Sidebar';
import styles from 'Frontend/views/MainView/MainView.module.css'

const MainView = () => {

  return (
    <body className={styles.body}>
    <Header/>
    <div className={styles.mainBody}>
      <Sidebar/>
    </div>
    </body>
  )

}

export default MainView