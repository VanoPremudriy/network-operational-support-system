import { useEffect, useState } from "react";
import { RouteEndpoint } from "Frontend/generated/endpoints";

interface Point {
  id: string;
  name: string;
  coordinates: [number, number];
  equipmentAmount: number;
}

interface Edge {
  source: string;
  target: string;
}

export const useRouteData = () => {
  const [data, setData] = useState<{ points: Point[]; edges: Edge[] } | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const route = await RouteEndpoint.getRoute();

        const points = Array.from(route?.nodes ?? [])
          .filter((node): node is Point => node !== undefined)
          .map((node) => ({
            id: node.id,
            name: node.name,
            coordinates: [
              Number(node.coordinates[0]),
              Number(node.coordinates[1]),
            ] as [number, number],
            equipmentAmount: node.equipmentAmount
          }));

        const edges = Array.from(route?.edges ?? [])
          .filter((edge): edge is Edge => edge !== undefined)
          .map((edge) => ({
            source: edge.source,
            target: edge.target,
          }));

        setData({ points, edges });
      } catch (error) {
        console.error("Ошибка при загрузке маршрута", error);
      }
    };

    fetchData();
  }, []);

  return data;
};
