import React, { useEffect, useRef, useMemo, useState } from "react";
import { select, zoom, geoPath, geoNaturalEarth1, zoomIdentity } from "d3";
import { Marks } from "./Marks";

type Point = { id: number; name: string; coordinates: [number, number] };
type Edge = { source: number; target: number };

type MapProps = {
  data: any;
  roads: any;
  lakes: any;
  points: Point[];
  edges: Edge[];
};

export const Map = ({ data, roads, lakes, points, edges }: MapProps) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const gRef = useRef<SVGGElement | null>(null);

  const [zoomScale, setZoomScale] = useState(1);
  const [transform, setTransform] = useState(zoomIdentity);

  const projection = useMemo(() => geoNaturalEarth1(), []);

  const pathGenerator = useMemo(() =>
    geoPath().
    projection(projection),
    [projection]);

  const getCoords = (coordinates: [number, number]) => {
    const projected = projection(coordinates);
    return { x: projected?.[0] || 0, y: projected?.[1] || 0 };
  };

  const [clearSelectionTrigger, setClearSelectionTrigger] = useState(0);

  const handleSvgClick = () => {
    setClearSelectionTrigger((prev) => prev + 1); // Триггер обновления
  };

  useEffect(() => {
    if (!data) return;

    const svg = select(svgRef.current!);
    const g = select(gRef.current!);

    let frame = 0;
    const zoomBehavior = zoom()
      .scaleExtent([1, 100])
      .translateExtent([[-1000, -1000], [1000, 1000]])
      .on("zoom", (event) => {
        const transform = event.transform;
        g.attr("transform", transform);
        cancelAnimationFrame(frame);
        frame = requestAnimationFrame(() => {
          setTransform(transform);
          setZoomScale(transform.k);
        });
      });

    svg.call(zoomBehavior as any);

    const initialTransform = zoomIdentity.translate(-2000, 300).scale(3);
    svg.call(zoomBehavior.transform as any, initialTransform);
    return () => cancelAnimationFrame(frame);
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

  const filteredData = useMemo(() => {
    if (!roads || !roads.features) return [];

    const bounds = getVisibleBounds();

    return roads.features.filter((feature: any) => {
      const minZoom = feature.properties.min_zoom || 0;
      const maxZoom = feature.properties.max_zoom || 100;

      if (zoomScale < minZoom || zoomScale > maxZoom) return false;

      let coordinates: any[] = [];

      // Поддержка MultiLineString и LineString
      if (feature.geometry.type === "LineString") {
        coordinates = [feature.geometry.coordinates];
      } else if (feature.geometry.type === "MultiLineString") {
        coordinates = feature.geometry.coordinates;
      } else {
        return false; // пропускаем всё остальное (например, Point)
      }

      return coordinates.some((segment) =>
        segment.some(([lon, lat]: [number, number]) => {
          const projected = projection([lon, lat]);
          if (!projected) return false;

          const [x, y] = projected;

          return (
            x >= bounds.xMin &&
            x <= bounds.xMax &&
            y >= bounds.yMin &&
            y <= bounds.yMax
          );
        })
      );
    });
  }, [roads, zoomScale, transform]);

  useEffect(() => {
    const g = select(svgRef.current).select(".roads");
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


  useEffect(() => {
    const g = select(svgRef.current).select(".map");
    g.selectAll("path").remove();

    g.selectAll("path")
      .data(data?.features || [])
      .enter()
      .append("path")
      .attr("d", (d: any) => pathGenerator(d) || "")  // Генерация пути для каждого feature
      .attr("fill", "#ccc")
      .attr("stroke", "#737171")
      .attr("stroke-width", 1 / zoomScale)
      .attr("pointer-events", "none");
  }, [data?.features, zoomScale]);

  useEffect(() => {
    if (!lakes || !lakes.features) return;

    const g = select(svgRef.current).select(".lakes");
    g.selectAll("path").remove();

    g.selectAll("path")
      .data(lakes?.features || [])
      .enter()
      .append("path")
      .attr("d", (d: any) => pathGenerator(d) || "")  // Генерация пути для каждого feature
      .attr("fill", "none")
      .attr("stroke", "#0b4d8f")
      .attr("stroke-width", 1 / zoomScale)
      .attr("pointer-events", "none");
  }, [lakes?.features, zoomScale]);

  return (
    <div style={{ position: 'relative', width: '100%', height: '100%' }}>
    {/*<MapWithOSM/>*/}
      <svg ref={svgRef} width="100%" height="100%" style={{ border: '1px solid black' }} onClick={handleSvgClick}>
        <rect width="100%" height="100%" fill="transparent" pointerEvents="all" />
        <g ref={gRef}>
          <g className="map" />
          <g className="roads" />
          <g className="lakes" />
          <Marks points={mappedPoints} edges={edges} zoomScale={zoomScale} clearSelectionTrigger={clearSelectionTrigger}/>
        </g>
      </svg>
    </div>
  );
};
