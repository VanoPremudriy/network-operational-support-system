import { select } from "d3";
import { useEffect } from "react";
import { useLakes } from 'Frontend/hooks/useMapData';

export const LakesLayer = ({pathGenerator, zoomScale }: any) => {

  const lakes = useLakes();

  useEffect(() => {
    if (!lakes?.features) return;
    const g = select(".lakes");
    g.selectAll("path").remove();

    g.selectAll("path")
      .data(lakes.features)
      .enter()
      .append("path")
      .attr("d", (d: any) => pathGenerator(d) || "")
      .attr("fill", "none")
      .attr("stroke", "#0b4d8f")
      .attr("stroke-width", 1 / zoomScale)
      .attr("pointer-events", "none");
  }, [lakes, zoomScale]);

  return (
    <g className={"lakes"}/>
  );
};