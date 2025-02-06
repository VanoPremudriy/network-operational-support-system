import { FeatureCollection } from "geojson";

export const fetchGeoData = async (): Promise<FeatureCollection> => {
  const response = await fetch('https://raw.githubusercontent.com/codeforgermany/click_that_hood/main/public/data/russia.geojson');
  if (!response.ok) {
    throw new Error('Ошибка при загрузке GeoJSON');
  }
  return await response.json();
};