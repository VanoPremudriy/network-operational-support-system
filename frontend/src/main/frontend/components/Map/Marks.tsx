import React, { useEffect, useRef, useMemo, useState } from 'react';
import { select, zoom, ZoomBehavior, geoPath, geoNaturalEarth1, zoomIdentity } from 'd3';

type Point = { id: number; name: string; coordinates: [number, number] };
type Edge = { source: number; target: number };

type MarksProps = {
  data: any; // GeoJSON для карты
  points: Point[];
  edges: Edge[];
};

export const Marks = ({ data, points, edges }: MarksProps) => {
  const gRef = useRef<SVGGElement | null>(null);
  const svgRef = useRef<SVGSVGElement | null>(null);

  useEffect(() => {
    if (!data) return;

    const svg = select(svgRef.current!);
    const g = select(gRef.current!);

    const zoomBehavior: ZoomBehavior<Element, unknown> = zoom()
      .scaleExtent([1, 20])
      .translateExtent([[-1000, -1000], [1000, 1000]])
      .on("zoom", (event) => {
        g.attr("transform", event.transform);
        setZoomScale(event.transform.k);
      });

    svg.call(zoomBehavior as any);

    // ✅ начальный масштаб и позиция
    const initialScale = 4; // подбери под себя
    const initialX = -2000;     // смещение по X
    const initialY = 300;      // смещение по Y

    const initialTransform = zoomIdentity
      .translate(initialX, initialY)
      .scale(initialScale);

    svg.call(zoomBehavior.transform as any, initialTransform);
  }, [data]);

  const projection = useMemo(() => geoNaturalEarth1(), []);
  const pathGenerator = useMemo(() => geoPath().projection(projection), [projection]);

  const getCoords = (coordinates: [number, number]) => {
    const projected = projection(coordinates);
    return { x: projected ? projected[0] : 0, y: projected ? projected[1] : 0 };
  };

  const [zoomScale, setZoomScale] = useState(1);

  return (
    <svg ref={svgRef} width="100%" height="100%" style={{ border: "1px solid black" }}>
      <rect
        width="100%"
        height="100%"
        fill="transparent"
        pointerEvents="all"
      />
      <g ref={gRef}>
        {/* Отображение карты */}
        {data.features.map((feature: any, i: number) => {
          const path = pathGenerator(feature);
          return path ? (
            <path key={i} d={path} fill="#ccc" stroke="#333" strokeWidth={0.1} />
          ) : null;
        })}

        {/* Отображение рёбер (линий) */}
        {edges.map((edge, i) => {
          const sourcePoint = points.find((p) => p.id === edge.source);
          const targetPoint = points.find((p) => p.id === edge.target);

          if (sourcePoint && targetPoint) {
            const sourceCoords = getCoords(sourcePoint.coordinates);
            const targetCoords = getCoords(targetPoint.coordinates);

            return (
              <line
                key={i}
                x1={sourceCoords.x}
                y1={sourceCoords.y}
                x2={targetCoords.x}
                y2={targetCoords.y}
                stroke="#000"
                strokeWidth={1 / zoomScale} // 🔁 тоже инвертируем
                // strokeWidth={0.1}
              />
            );
          }
          return null;
        })}

        {/* Отображение точек */}
        {points.map((point) => {
          const { x, y } = getCoords(point.coordinates);
          return (
            <circle
              key={point.id}
              cx={x}
              cy={y}
              r={4 / zoomScale} // 🔁 инверсия масштаба
              fill="red"
              stroke="#333"
              // strokeWidth={0.5}
              strokeWidth={1 / zoomScale} // 🔁
            >
              <title>{point.name}</title>
            </circle>
          );
        })}
      </g>
    </svg>
  );
};