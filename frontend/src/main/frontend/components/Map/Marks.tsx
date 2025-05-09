import React, { useState } from 'react';

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

export const Marks = ({ points, edges, zoomScale, clearSelectionTrigger }: MarksProps) => {
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
            stroke="#000"
            strokeWidth={1 / zoomScale}
          />
        );
      })}

      {/* Точки */}
      {points.map((point) => (
        <circle
          key={point.id}
          cx={point.x}
          cy={point.y}
          r={4 / zoomScale}
          fill="red"
          stroke="#333"
          strokeWidth={1 / zoomScale}
          onClick={(e) => {
            e.stopPropagation(); // <-- вот это важно!
            setSelectedPoint(point);
          }}
        >
          <title>{point.name}</title>
        </circle>
      ))}

      {/* Всплывающее окно с инфой */}
      {selectedPoint && (
          <g>
            <g
              transform={`translate(${selectedPoint.x + 10 / zoomScale}, ${selectedPoint.y - 10 / zoomScale}) scale(${1 / zoomScale })`}
            >
              <rect
                x={0}
                y={0}
                width={120}
                height={40}
                fill="white"
                stroke="black"
                strokeWidth={0.5}
                rx={4}
                ry={4}
              />
              <text x={10} y={15} fontSize={10} fill="black">
                {`ID: ${selectedPoint.id}`}
              </text>
              <text x={10} y={30} fontSize={10} fill="black">
                {`Name: ${selectedPoint.name}`}
              </text>
            </g>
          </g>
      )}
    </>
  );
};