import React, { useEffect, useState } from "react";
import { select, zoom, zoomIdentity } from "d3";

export const useMapZoom = (svgRef: React.RefObject<SVGSVGElement>, gRef: React.RefObject<SVGGElement>, data: any, initialTransform = zoomIdentity) => {
  const [transform, setTransform] = useState(zoomIdentity);
  const [zoomScale, setZoomScale] = useState(1);

  useEffect(() => {
    if (!data || !svgRef.current || !gRef.current) return;

    const svg = select(svgRef.current);
    const g = select(gRef.current);

    let frame = 0;

    const zoomBehavior = zoom()
      .scaleExtent([1, 100])
      .translateExtent([[-1000, -1000], [1000, 1000]])
      .on("zoom", (event) => {
        const t = event.transform;
        g.attr("transform", t);
        cancelAnimationFrame(frame);
        frame = requestAnimationFrame(() => {
          setTransform(t);
          setZoomScale(t.k);
        });
      });

    svg.call(zoomBehavior as any);
    svg.call(zoomBehavior.transform as any, initialTransform);

    return () => cancelAnimationFrame(frame);
  }, [data]);

  return { transform, zoomScale };
};
