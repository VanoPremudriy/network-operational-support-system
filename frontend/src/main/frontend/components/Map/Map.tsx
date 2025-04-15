import React from "react";
import { Marks } from "./Marks";

type MapProps = {
  data: any;
  points: { id: number; name: string; coordinates: [number, number] }[];
  edges: { source: number; target: number }[];
};

export const Map = ({ data, points, edges }: MapProps) => {
  return (
    <svg width="100%" height="100%" style={{ border: "1px solid black" }}>
      <Marks data={data} points={points} edges={edges} />
    </svg>
  );
};
