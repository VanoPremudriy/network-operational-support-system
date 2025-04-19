import { FeatureCollection } from "geojson";
import {GeoEndpoint} from "Frontend/generated/endpoints"

export const fetchGeoData = async (): Promise<FeatureCollection> => {
  const response = await GeoEndpoint.getRussianGeoData();
  if (!response) {
    throw new Error('Ошибка при загрузке GeoJSON');
  }
  return  JSON.parse(response) as FeatureCollection;
};

export const fetchGeoDataRoads = async (): Promise<FeatureCollection> => {
  const response = await GeoEndpoint.getRoads();
  if (!response) {
    throw new Error('Ошибка при загрузке GeoJSON');
  }
  return  JSON.parse(response) as FeatureCollection;
};

export const fetchGeoDataLakes = async (): Promise<FeatureCollection> => {
  const response = await GeoEndpoint.getLakes();
  if (!response) {
    throw new Error("Ошибка при загрузке GeoJson")
  }
  return JSON.parse(response) as FeatureCollection;
}