import ClientList from 'Frontend/components/Client/ClientList/ClientList';
import ClientSearch from 'Frontend/components/Client/ClientSearch/ClientSearch';
import { useState } from 'react';
import styles from 'Frontend/views/ClientView/ClientView.module.css'
import { FaPlus } from "react-icons/fa"
import CustomButtonColorWithIcon from 'Frontend/components/Buttons/CustomButtonColorWithIcon/CustomButtonColorWithIcon';
import { Client } from 'Frontend/components/Client/ClientRow/ClientRow';
import ClientUpdateForm from 'Frontend/components/Client/ClientUpdateForm/ClientUpdateForm';

const ClientView = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedClient, setSelectedClient] = useState<Client | null>(null);

  const handleAddClient = () => {
    console.log('Добавить клиента');
  };

  const handleSave = (updatedClient: Client) => {
    console.log('Сохранён клиент:', updatedClient);
    // Здесь можно обновить список клиентов
  };

  const handleUpdate = (client: Client) => {
    setSelectedClient(client);
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
        <ClientList onUpdate={handleUpdate} />
      </div>

    </div>
      {selectedClient && (
        <ClientUpdateForm
          client={selectedClient}
          onClose={() => setSelectedClient(null)}
          onSave={handleSave}
        />
      )}
    </div>
  );
};

export default ClientView;
