import {useState, useEffect} from "react";
import {json} from "d3";
import * as topojson from 'topojson-client';
import {FeatureCollection} from 'geojson'

const topojsonUrl = 'https://unpkg.com/world-atlas@2.0.2/countries-110m.json';

export const useData = (): FeatureCollection=> {
    // @ts-ignore
    const [data, setData] = useState<FeatureCollection> (null);

    useEffect(() => {
        fetch(topojsonUrl)
            .then(response => response.json())
            .then(topojsonData => {
                const {countries} = topojsonData.objects;
                setData(topojson.feature(topojsonData, countries) as unknown as FeatureCollection);
            })
    }, []);

    return data;
};