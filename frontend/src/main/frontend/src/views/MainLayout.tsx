import React, { useEffect } from "react";
import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { signal, effect, Signal } from '@vaadin/hilla-react-signals';
import { Map } from "../components/Map";
import { useMapData } from "../hooks/useMapData";

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
  // остальные точки…
];

const edges = [
  { source: 2, target: 3 },
  // остальные ребра…
];

const width = 2500;
const height = 5000;

export default function MainLayout() {
  const currentTitle = useViewConfig()?.title ?? '';
  const data = useMapData();

  useEffect(() => {
    vaadin.documentTitleSignal.value = currentTitle;
  }, [currentTitle]);

  if (!data) {
    return <pre>Loading...</pre>;
  }

  return (
    <div>
      <Map data={data} points={points} edges={edges} width={width} height={height} />
    </div>
  );
}
