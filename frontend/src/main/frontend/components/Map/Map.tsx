import React, {useRef, useMemo, useState } from "react";
import {geoPath, geoNaturalEarth1, zoomIdentity } from "d3";
import { Marks } from "./Marks";
import {useMapZoom} from 'Frontend/hooks/useMapZoom';
import {useVisibleBounds} from 'Frontend/hooks/useVisibleBounds';
import { BaseMapLayer } from 'Frontend/components/Map/Layers/BaseMapLayer';
import { LakesLayer } from 'Frontend/components/Map/Layers/LakesLayer';
import { RoadsLayer } from 'Frontend/components/Map/Layers/RoadsLayer';

type Point = { id: string; name: string; coordinates: [number, number]; equipmentAmount: number};
type Edge = { source: string; target: string };

type MapProps = {
  data: any;
  points: Point[];
  edges: Edge[];
};

export const Map = ({ data, points, edges }: MapProps) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const gRef = useRef<SVGGElement | null>(null);

  const [clearSelectionTrigger, setClearSelectionTrigger] = useState(0);
  const handleSvgClick = () => setClearSelectionTrigger(prev => prev + 1);

  const projection = useMemo(() => geoNaturalEarth1(), []);
  const pathGenerator = useMemo(() => geoPath().projection(projection), [projection]);

  const { transform, zoomScale } = useMapZoom(svgRef, gRef, data, zoomIdentity.translate(-2000, 300).scale(3));
  const visibleBounds = useVisibleBounds(svgRef, transform, projection);

  const mappedPoints = useMemo(() =>
      points.map((p) => {
        const [x, y] = projection(p.coordinates) || [0, 0];
        return { ...p, x, y };
      }),
    [points, projection]
  );

  return (
    <div style={{ position: 'relative', width: '100%', height: '100%' }}>
      <svg ref={svgRef} width="100%" height="100%" style={{ border: '1px solid black' }} onClick={handleSvgClick}>
        <rect width="100%" height="100%" fill="transparent" pointerEvents="all" />
        <g ref={gRef}>
          <BaseMapLayer features={data?.features || []} pathGenerator={pathGenerator} zoomScale={zoomScale} />
          <RoadsLayer pathGenerator={pathGenerator} zoomScale={zoomScale} bounds={visibleBounds} projection={projection} />
          <LakesLayer pathGenerator={pathGenerator} zoomScale={zoomScale} />
          <Marks points={mappedPoints} edges={edges} zoomScale={zoomScale} clearSelectionTrigger={clearSelectionTrigger} />
        </g>
      </svg>
    </div>
  );
};
