import React from "react";
import { Marks } from "./Marks";

type MapProps = {
  data: any;
  points: { id: number; name: string; coordinates: [number, number] }[];
  edges: { source: number; target: number }[];
  width: number;
  height: number;
};

export const Map = ({ data, points, edges, width, height }: MapProps) => {
  return (
    <svg width={width} height={height} style={{ border: "1px solid black" }}>
      <Marks data={data} points={points} edges={edges} />
    </svg>
  );
};
