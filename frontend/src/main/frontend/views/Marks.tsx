import React, { useEffect, useRef } from "react";
import { select, zoom, ZoomBehavior, geoPath, geoNaturalEarth1 } from "d3";

type MarksProps = {
    data: any; // GeoJSON для карты
    points: { id: number; name: string; coordinates: [number, number] }[];
    edges: { source: number; target: number }[];
};


export const Marks = ({ data, points, edges }: MarksProps) => {
    const svgRef = useRef<SVGSVGElement | null>(null);
    const gRef = useRef<SVGGElement | null>(null);

    

    useEffect(() => {
        if (!data) return;

        const svg = select<SVGSVGElement, unknown>(svgRef.current!);
        const g = select<SVGGElement, unknown>(gRef.current!);

        // Инициализация масштабирования
        const zoomBehavior: ZoomBehavior<Element, unknown> = zoom()
            .scaleExtent([1, 8])
            .on("zoom", (event) => {
                g.attr("transform", event.transform);
            });

        svg.call(zoomBehavior as any);
    }, [data]);

    const projection = geoNaturalEarth1();
    const pathGenerator = geoPath().projection(projection);

    // Функция для преобразования координат в SVG
    const getCoords = (coordinates: [number, number]) => {
        const [x, y] = projection(coordinates) || [0, 0];
        return { x, y };
    };

    return (
        <svg ref={svgRef} width={2500} height={5000} style={{ border: "1px solid black" }}>
            <g ref={gRef}>
                {/* Отображение карты */}
                {data.features.map((feature: any, i: number) => {
                    const path = pathGenerator(feature);
                    return path ? (
                        <path key={i} d={path} fill="#ccc" stroke="#333" strokeWidth={0.1} />
                    ) : null;
                })}

                {/* Отображение рёбер (линий) */}
                {edges.map((edge, i) => {
                    const sourcePoint = points.find((p) => p.id === edge.source);
                    const targetPoint = points.find((p) => p.id === edge.target);

                    if (sourcePoint && targetPoint) {
                        const sourceCoords = getCoords(sourcePoint.coordinates);
                        const targetCoords = getCoords(targetPoint.coordinates);

                        return (
                            <line
                                key={i}
                                x1={sourceCoords.x}
                                y1={sourceCoords.y}
                                x2={targetCoords.x}
                                y2={targetCoords.y}
                                stroke="#000"
                                strokeWidth={0.1}
                            />
                        );
                    }
                    return null;
                })}

                {/* Отображение точек */}
                {points.map((point) => {
                    const { x, y } = getCoords(point.coordinates);

                    return (
                        <circle
                            key={point.id}
                            cx={x}
                            cy={y}
                            r={1}
                            fill="red"
                            stroke="#333"
                            strokeWidth={0.5}
                        >
                            <title>{point.name}</title> {/* Подсказка с названием точки */}
                        </circle>
                    );
                })}
            </g>
        </svg>
    );
};
