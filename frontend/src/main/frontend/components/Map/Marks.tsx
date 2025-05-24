import React, { useState } from 'react';
import { PointTooltip } from 'Frontend/components/Map/PointTooltip';

type Point = {
  id: string;
  name: string;
  x: number;
  y: number;
  equipmentAmount: number;
};

type Edge = { source: string; target: string };

type MarksProps = {
  points: Point[];
  edges: Edge[];
  zoomScale: number;
  clearSelectionTrigger: number;
};

export const Marks = ({ points, edges, zoomScale, clearSelectionTrigger, onPointClick }:  MarksProps & { onPointClick: (id: string) => void }) => {
  const [selectedPoint, setSelectedPoint] = useState<Point | null>(null);

  React.useEffect(() => {
    setSelectedPoint(null);
  }, [clearSelectionTrigger]);

  return (
    <>
      {/* Ребра */}
      {edges.map((edge, i) => {
        const source = points.find((p) => p.id === edge.source);
        const target = points.find((p) => p.id === edge.target);

        if (!source || !target) return null;

        return (
          <line
            key={i}
            x1={source.x}
            y1={source.y}
            x2={target.x}
            y2={target.y}
            stroke="#E32636"
            strokeWidth={2 / zoomScale}
          />
        );
      })}

      {/* Точки */}
      {points.map((point) => (
        <circle
          key={point.id}
          cx={point.x}
          cy={point.y}
          r={6 / zoomScale}
          fill="red"
          stroke="#333"
          strokeWidth={2 / zoomScale}
          onClick={(e) => {
            e.stopPropagation(); // <-- вот это важно!
            setSelectedPoint(point);
          }}
        >
          <title>{point.name}</title>
        </circle>
      ))}


      {/* Всплывающее окно */}
      {selectedPoint && <PointTooltip point={selectedPoint} zoomScale={zoomScale} onDetailsClick={() => onPointClick(selectedPoint.id)}/>}
    </>
  );
};