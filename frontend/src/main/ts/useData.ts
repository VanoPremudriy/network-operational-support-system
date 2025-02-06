import {useState, useEffect} from "react";
import {json} from "d3";
import * as topojson from 'topojson-client';
import {FeatureCollection} from 'geojson'


//const topojsonUrl = 'https://unpkg.com/world-atlas@2.0.2/countries-110m.json';

export const useData = (): FeatureCollection=> {
    // @ts-ignore
    const [data, setData] = useState<FeatureCollection> (null);

    useEffect(() => {
      fetch('https://raw.githubusercontent.com/codeforgermany/click_that_hood/main/public/data/russia.geojson')
        .then(response => response.json())
        .then((geojsonData) => {
          setData(geojsonData);
        })
        .catch(error => console.error('Ошибка при загрузке GeoJSON:', error));
    }, []);

    return data;
};





// export const useData = (): FeatureCollection | null => {
//   const [data, setData] = useState<FeatureCollection | null>(null);
//
//   useEffect(() => {
//     fetch(topojsonUrl)
//       .then((response) => response.json())
//       .then((geojsonData) => {
//         setData(geojsonData);
//       })
//       .catch((error) => console.error("Error loading GeoJSON:", error));
//   }, []);
//
//   return data;
// };