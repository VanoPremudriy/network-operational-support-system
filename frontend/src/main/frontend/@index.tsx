import { createElement } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { router } from 'Frontend/routes';

const outlet = document.getElementById('outlet')!;
const root = (outlet as any)._root ?? createRoot(outlet);
(outlet as any)._root = root;

root.render(createElement(() => <RouterProvider router={router} />));