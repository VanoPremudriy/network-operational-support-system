import React from "react";

type Point = {
  id: number;
  name: string;
  x: number;
  y: number;
};

type Edge = { source: number; target: number };

type MarksProps = {
  points: Point[];
  edges: Edge[];
  zoomScale: number;
};

export const Marks = ({ points, edges, zoomScale }: MarksProps) => {
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
        >
          <title>{point.name}</title>
        </circle>
      ))}
    </>
  );
};