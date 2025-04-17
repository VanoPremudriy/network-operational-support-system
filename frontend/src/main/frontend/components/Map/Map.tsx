import React, { useEffect, useRef, useMemo, useState } from "react";
import { select, zoom, geoPath, geoNaturalEarth1, zoomIdentity } from "d3";
import { Marks } from "./Marks";

type Point = { id: number; name: string; coordinates: [number, number] };
type Edge = { source: number; target: number };

type MapProps = {
  data: any;
  points: Point[];
  edges: Edge[];
};

export const Map = ({ data, points, edges }: MapProps) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const gRef = useRef<SVGGElement | null>(null);

  const [zoomScale, setZoomScale] = useState(1);

  // const projection = useMemo(() => geoNaturalEarth1(), []);

  const projection = useMemo(() =>
      geoNaturalEarth1()
        .scale(1100) // подбирается вручную, можно увеличить
        .center([100, 65]) // центр России
        .translate([400, 250]), // центр SVG: width / 2, height / 2
    []);

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
      .scaleExtent([1, 20])
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

  return (
    <svg ref={svgRef} width="100%" height="100%" style={{ border: "1px solid black" }}>
      <rect width="100%" height="100%" fill="transparent" pointerEvents="all" />
      <g ref={gRef}>
        {/* Гео-объекты (карта) */}
        {data?.features.map((feature: any, i: number) => {
          const path = pathGenerator(feature);
          return path ? (
            <path key={i} d={path} fill="#ccc" stroke="#333" strokeWidth={0.1} />
          ) : null;
        })}

        {/* Узлы и связи */}
        <Marks points={mappedPoints} edges={edges} zoomScale={zoomScale} />
      </g>
    </svg>
  );
};
