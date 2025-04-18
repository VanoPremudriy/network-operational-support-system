import React, { useEffect, useRef, useMemo, useState } from "react";
import { select, zoom, geoPath, geoNaturalEarth1, zoomIdentity } from "d3";
import { Marks } from "./Marks";

type Point = { id: number; name: string; coordinates: [number, number] };
type Edge = { source: number; target: number };

type MapProps = {
  data: any;
  details: any;
  points: Point[];
  edges: Edge[];
};

export const Map = ({ data, details, points, edges }: MapProps) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const gRef = useRef<SVGGElement | null>(null);

  const [zoomScale, setZoomScale] = useState(1);
  const [transform, setTransform] = useState(zoomIdentity);

  const projection = useMemo(() => geoNaturalEarth1(), []);

  const pathGenerator = useMemo(() => geoPath().projection(projection), [projection]);

  const getCoords = (coordinates: [number, number]) => {
    const projected = projection(coordinates);
    return { x: projected?.[0] || 0, y: projected?.[1] || 0 };
  };

  useEffect(() => {
    if (!data) return;

    const svg = select(svgRef.current!);
    const g = select(gRef.current!);

    const zoomBehavior = zoom()
      .scaleExtent([1, 100])
      .translateExtent([[-1000, -1000], [1000, 1000]])
      .on("zoom", (event) => {
        g.attr("transform", event.transform);
        setZoomScale(event.transform.k);
      });

    svg.call(zoomBehavior as any);

    const initialTransform = zoomIdentity.translate(-2000, 300).scale(4);
    svg.call(zoomBehavior.transform as any, initialTransform);
  }, [data]);

  const mappedPoints = points.map((p) => ({
    ...p,
    ...getCoords(p.coordinates),
  }));

  const getVisibleBounds = () => {
    const width = svgRef.current?.clientWidth || 800;
    const height = svgRef.current?.clientHeight || 600;

    // Получаем границы видимой области в screen space (после применения трансформации)
    const topLeft = transform.invert([0, 0]);
    const bottomRight = transform.invert([width, height]);

    return {
      xMin: topLeft[0],
      yMin: topLeft[1],
      xMax: bottomRight[0],
      yMax: bottomRight[1],
    };
  };

  const filteredData = details?.features.filter((feature: any) => {
    const minZoom = feature.properties.min_zoom || 0;
    const maxZoom = feature.properties.max_zoom || 100;

    // Проверка масштаба
    if (zoomScale < minZoom || zoomScale > maxZoom) {
      return false;
    }

    // Получаем координаты линии дороги (или многоугольника)
    const coords = feature.geometry.coordinates;
    if (!coords) return false;

    // Проверяем, попадает ли хотя бы одна точка дороги в видимую область
    const bounds = getVisibleBounds();
    return coords.some(([lon, lat]: [number, number]) => {
      const projected = projection([lon, lat]);
      if (!projected) return false; // Если проекция null, игнорируем точку

      return (
        projected[0] >= bounds.xMin &&
        projected[0] <= bounds.xMax &&
        projected[1] >= bounds.yMin &&
        projected[1] <= bounds.yMax
      );
    });
  });

  return (
    <svg ref={svgRef} width="100%" height="100%" style={{ border: "1px solid black" }}>
      <rect width="100%" height="100%" fill="transparent" pointerEvents="all" />
      <g ref={gRef}>
        {/* Гео-объекты (карта) */}
        {data?.features.map((feature: any, i: number) => {
          const path = pathGenerator(feature);
          return path ? (
            <path key={i} d={path} fill="#ccc" stroke="#333" strokeWidth={1 / zoomScale} />
          ) : null;
        })}

        {filteredData?.map((feature: any, i: number) => {
          const path = pathGenerator(feature);
          return path ? (
            <path
              key={i}
              d={path}
              fill="none"
              stroke="black"
              strokeWidth={1 / zoomScale}
            />
          ) : null;
        })}

        {/* Узлы и связи */}
        <Marks points={mappedPoints} edges={edges} zoomScale={zoomScale} />
      </g>
    </svg>
  );
};
