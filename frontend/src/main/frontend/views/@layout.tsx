import React, { useEffect, useState } from 'react';
import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { signal, effect, Signal } from '@vaadin/hilla-react-signals';
import { Map } from "Frontend/components/Map/Map";
import { useMapData } from "Frontend/hooks/useMapData";
import Header from 'Frontend/components/Header/Header';
import AuthorizationView from 'Frontend/views/AuthorizationView/AuthorizationView';
import MainView from 'Frontend/views/MainView/MainView';
import Authorization from 'Frontend/components/Authorization/Authorization';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RouteMap from 'Frontend/components/Map/RouteMap';

// Пример глобального сигнала для заголовка
const vaadin = window.Vaadin as { documentTitleSignal: Signal<string> };
vaadin.documentTitleSignal = signal('');

effect(() => {
  document.title = vaadin.documentTitleSignal.value;
});

// Пример данных для графа (можно вынести в отдельный domain-модуль)


export default function Layout() {
  const currentTitle = useViewConfig()?.title ?? '';
  const data = useMapData();

  // useEffect(() => {
  //   vaadin.documentTitleSignal.value = currentTitle;
  // }, [currentTitle]);
  //
  // if (!data) {
  //   return <pre>Loading...</pre>;
  // }

  const [authorized, setAuthorized] = useState(false);

  return (
    // <body>
    //   {/*{authorized ? <MainView /> : <AuthorizationView />}*/}
    //   {/*<AuthorizationView />*/}
    //   {/*<MainView/>*/}
    //   {
    //   }
    //   {/*{localStorage.getItem("token") ? <MainView /> : <AuthorizationView setAuthorized={setAuthorized} />}*/}
    // {/*<Navigation/>*/}
    // <RouteMap />
    // </body>

      <Routes>
        <Route path="/*"
               element={localStorage.getItem("token") ?
                 <MainView /> :
                 <AuthorizationView setAuthorized={setAuthorized}/>} />
      </Routes>

  );
}
