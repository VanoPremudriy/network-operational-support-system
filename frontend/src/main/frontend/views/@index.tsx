import React from 'react';
import ReactDOM from "react-dom/client"; // Используем client API

import Layout from 'Frontend/views/@layout';

const root = ReactDOM.createRoot(document.getElementById("outlet")!);
root.render(
  <React.StrictMode>
    <Layout />
  </React.StrictMode>
);