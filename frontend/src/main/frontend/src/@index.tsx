import React from 'react';
import ReactDOM from "react-dom/client"; // Используем client API

import MainLayout from 'Frontend/src/views/MainLayout';

const root = ReactDOM.createRoot(document.getElementById("outlet")!);
root.render(
  <React.StrictMode>
    <MainLayout />
  </React.StrictMode>
);