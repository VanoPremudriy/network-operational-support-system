import React, { useEffect, useState } from 'react';
import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { signal, effect, Signal } from '@vaadin/hilla-react-signals';
import { Map } from "Frontend/components/Map";
import { useMapData } from "Frontend/hooks/useMapData";
import Header from 'Frontend/components/Header/Header';
import AuthorizationView from 'Frontend/views/AuthorizationView/AuthorizationView';
import MainView from 'Frontend/views/MainView/MainView';

// Пример глобального сигнала для заголовка
const vaadin = window.Vaadin as { documentTitleSignal: Signal<string> };
vaadin.documentTitleSignal = signal('');

effect(() => {
  document.title = vaadin.documentTitleSignal.value;
});

// Пример данных для графа (можно вынести в отдельный domain-модуль)
const points : { id: number; name: string; coordinates: [number, number] }[] = [
  { id: 1, name: "Moscow", coordinates: [37.6173, 55.7558] },
  { id: 2, name: "Saint Petersburg", coordinates: [30.3351, 59.9343] },
  { id: 3, name: "Novosibirsk", coordinates: [82.9346, 55.0084] },
  { id: 4, name: "Yekaterinburg", coordinates: [60.6122, 56.8519] },
  { id: 5, name: "Kazan", coordinates: [49.1233, 55.7887] },
  { id: 6, name: "Nizhny Novgorod", coordinates: [44.0059, 56.3287] },
  { id: 7, name: "Chelyabinsk", coordinates: [61.4291, 55.1644] },
  { id: 8, name: "Omsk", coordinates: [73.3686, 54.9924] },
  { id: 9, name: "Samara", coordinates: [50.1500, 53.2028] },
  { id: 10, name: "Rostov-on-Don", coordinates: [39.7015, 47.2225] },
  { id: 11, name: "Ufa", coordinates: [56.0381, 54.7388] },
  { id: 12, name: "Krasnoyarsk", coordinates: [92.8526, 56.0106] },
  { id: 13, name: "Perm", coordinates: [56.2294, 58.0105] },
  { id: 14, name: "Voronezh", coordinates: [39.2003, 51.6720] },
  { id: 15, name: "Volgograd", coordinates: [44.5168, 48.7080] }
  // остальные точки…
];

const edges = [
  { source: 2, target: 3 },
  // остальные ребра…
];

const width = 2500;
const height = 5000;

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
    <body>
      {/*{authorized ? <MainView /> : <AuthorizationView />}*/}
      {/*<AuthorizationView />*/}
      <MainView/>
    {/*<Navigation/>*/}
    {/*<Map data={data} points={points} edges={edges} width={width} height={height} />*/}
    </body>
  );
}
