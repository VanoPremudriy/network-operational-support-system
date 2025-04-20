import React from 'react';

export const useVisibleBounds = (
  svgRef: React.RefObject<SVGSVGElement>,
  transform: any,
  projection: any
) => {
  const width = svgRef.current?.clientWidth || 800;
  const height = svgRef.current?.clientHeight || 600;

  const topLeft = transform.invert([0, 0]);
  const bottomRight = transform.invert([width, height]);

  return {
    xMin: topLeft[0],
    yMin: topLeft[1],
    xMax: bottomRight[0],
    yMax: bottomRight[1],
  };
};