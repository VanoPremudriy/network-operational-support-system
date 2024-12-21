import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { useEffect } from 'react';
import { Signal, signal, effect } from '@vaadin/hilla-react-signals';
import {Marks} from "Frontend/views/Marks";
import {useData} from "../../ts/useData";

const vaadin = window.Vaadin as {
  documentTitleSignal: Signal<string>;
};
vaadin.documentTitleSignal = signal('');
effect(() => {
  document.title = vaadin.documentTitleSignal.value;
});

const width = 2500;
const  height = 1600;

const points: { id: number; name: string; coordinates: [number, number] }[] = [
    { id: 1, name: "Point A", coordinates: [-10, 20] },
    { id: 2, name: "Point B", coordinates: [50, 30] },
    { id: 3, name: "Point C", coordinates: [30, 60] },
    { id: 4, name: "London", coordinates: [-0.1278, 51.5074]}
];

const edges = [
    { source: 1, target: 2 },
    { source: 2, target: 3 },
];

export default function MainLayout() {
  const currentTitle = useViewConfig()?.title ?? '';
  const data = useData();

  useEffect(() => {
    vaadin.documentTitleSignal.value = currentTitle;
  });

    if (!data) {
        return <pre>Loading...</pre>;
    }

  return (
   <div>
       <svg width={width} height={height}>
           <Marks data={data} points={points} edges={edges}/>
       </svg>
   </div>
  );
}
