import { FeatureCollection } from "geojson";
import {GeoEndpoint} from "Frontend/generated/endpoints"

export const fetchGeoData = async (): Promise<FeatureCollection> => {
  const response = await GeoEndpoint.getRussianGeoData();
  if (!response) {
    throw new Error('Ошибка при загрузке GeoJSON');
  }
  return  JSON.parse(response) as FeatureCollection;
};


export const fetchGeoDataDetails = async (): Promise<FeatureCollection> => {
  const response = await GeoEndpoint.getRussianDetails();
  if (!response) {
    throw new Error('Ошибка при загрузке GeoJSON');
  }
  return  JSON.parse(response) as FeatureCollection;
};