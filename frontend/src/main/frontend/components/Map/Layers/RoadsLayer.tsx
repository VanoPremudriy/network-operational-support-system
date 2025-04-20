import { select } from "d3";
import { useEffect, useMemo } from "react";
import { useRoads } from 'Frontend/hooks/useMapData';

export const RoadsLayer = ({pathGenerator, zoomScale, bounds, projection }: any) => {

  const roads = useRoads();

  const filteredData = useMemo(() => {
    if (!roads?.features) return [];
    return roads.features.filter((feature: any) => {
      const minZoom = feature.properties.min_zoom || 0;
      const maxZoom = feature.properties.max_zoom || 100;
      if (zoomScale < minZoom || zoomScale > maxZoom) return false;

      let coordinates: any[] = [];
      if (feature.geometry.type === "LineString") {
        coordinates = [feature.geometry.coordinates];
      } else if (feature.geometry.type === "MultiLineString") {
        coordinates = feature.geometry.coordinates;
      } else return false;

      return coordinates.some((segment) =>
        segment.some(([lon, lat]: [number, number]) => {
          const projected = projection([lon, lat]);
          if (!projected) return false;
          const [x, y] = projected;
          return x >= bounds.xMin && x <= bounds.xMax && y >= bounds.yMin && y <= bounds.yMax;
        })
      );
    });
  }, [roads, zoomScale, bounds]);

  useEffect(() => {
    const g = select(".roads");
    g.selectAll("path").remove();

    g.selectAll("path")
      .data(filteredData)
      .enter()
      .append("path")
      .attr("d", (d: any) => pathGenerator(d) || "")
      .attr("fill", "none")
      .attr("stroke", "black")
      .attr("stroke-width", 1 / zoomScale)
      .attr("pointer-events", "none");
  }, [filteredData, zoomScale]);

  return (
    <g className={"roads"}/>
  );
};