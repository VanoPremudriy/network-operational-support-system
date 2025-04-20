import { select } from "d3";
import { useEffect } from "react";

export const BaseMapLayer = ({features, pathGenerator, zoomScale }: any) => {
  useEffect(() => {
    const g = select(".map");
    g.selectAll("path").remove();

    g.selectAll("path")
      .data(features)
      .enter()
      .append("path")
      .attr("d", (d: any) => pathGenerator(d) || "")
      .attr("fill", "#ccc")
      .attr("stroke", "#737171")
      .attr("stroke-width", 1 / zoomScale)
      .attr("pointer-events", "none");
  }, [features, zoomScale]);

  return (
    <g className={"map"}/>
  );
};