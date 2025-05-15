import ClientList from 'Frontend/components/Client/ClientList/ClientList';
import ClientSearch from 'Frontend/components/Client/ClientSearch/ClientSearch';
import { useState } from 'react';
import styles from 'Frontend/views/ClientView/ClientView.module.css'
import { FaPlus } from "react-icons/fa"
import CustomButtonColorWithIcon from 'Frontend/components/Buttons/CustomButtonColorWithIcon/CustomButtonColorWithIcon';
import { Client } from 'Frontend/types/Client';
import ClientUpdateForm from 'Frontend/components/Client/ClientUpdateForm/ClientUpdateForm'

const ClientView = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedClient, setSelectedClient] = useState<Client | null>(null);
  const [formMode, setFormMode] = useState<'create' | 'edit'>('create');
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [refreshTrigger, setRefreshTrigger] = useState(0);


  const handleAddClient = () => {
    setFormMode('create');
    setSelectedClient(null);
    setIsFormOpen(true);
  };

  const handleUpdate = (client: Client) => {
    setFormMode('edit');
    setSelectedClient(client);
    setIsFormOpen(true);
  };

  return (
    <div className={styles.mainContainer}>
    <div className={styles.container}>
      <div className={styles.header}>
        <h1 className={styles.title}>Клиенты</h1>
        <CustomButtonColorWithIcon label={"Добавить клиента"} onClick={handleAddClient} icon={<FaPlus/>} iconPosition={"right"}/>
      </div>

      <div className={styles.list}>
        <ClientSearch query={searchQuery} onChange={setSearchQuery} />
        <ClientList onUpdate={handleUpdate} refreshTrigger={refreshTrigger} setRefreshTrigger={setRefreshTrigger} />

      </div>

    </div>
      {isFormOpen && (
        <ClientUpdateForm
          client={selectedClient ?? undefined}
          mode={formMode}
          onClose={() => {
            setIsFormOpen(false);
            setSelectedClient(null);
          }}
          onSave={() => {
            setIsFormOpen(false);
            setSelectedClient(null);
            setRefreshTrigger(Date.now());
          }}
        />
      )}
    </div>
  );
};

export default ClientView;
