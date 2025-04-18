import { useState, useEffect } from "react";
import { FeatureCollection } from "geojson";
import { fetchGeoData, fetchGeoDataDetails } from '../services/geoService';

export const useMapData = (): FeatureCollection | null => {
  const [data, setData] = useState<FeatureCollection | null>(null);

  useEffect(() => {
    // Вызов сервиса для загрузки данных
    fetchGeoData()
      .then((geojsonData) => {
        setData(geojsonData);
      })
      .catch((error) => {
        console.error("Ошибка при загрузке GeoJSON:", error);
      });
  }, []);

  return data;
};

export const useDetails = (): FeatureCollection | null => {
  const [data, setData] = useState<FeatureCollection | null>(null);

  useEffect(() => {
    // Вызов сервиса для загрузки данных
    fetchGeoDataDetails()
      .then((geojsonData) => {
        setData(geojsonData);
      })
      .catch((error) => {
        console.error("Ошибка при загрузке GeoJSON:", error);
      });
  }, []);

  return data;
};