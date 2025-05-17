import React from 'react';

type Point = {
  id: string;
  name: string;
  x: number;
  y: number;
  equipmentAmount: number;
};

type PointTooltipProps = {
  point: Point;
  zoomScale: number;
  onDetailsClick?: (id: string) => void;
};

export const PointTooltip: React.FC<PointTooltipProps> = ({ point, zoomScale, onDetailsClick }) => {
  const width = 220;
  const height = 110;

  const x = point.x + 10 / zoomScale;
  const y = point.y - height / 2 / zoomScale;

  return (
    <g transform={`translate(${x}, ${y})`} style={{ cursor: 'default' }}>
      {/* Фон тултипа */}
      <rect
        width={width / zoomScale}
        height={height / zoomScale}
        rx={12 / zoomScale}
        ry={12 / zoomScale}
        fill="white"
        stroke="#ccc"
        strokeWidth={1 / zoomScale}
      />
      {/* Текст и кнопка */}
      <text
        x={10 / zoomScale}
        y={25 / zoomScale}
        fontSize={16 / zoomScale}
        fontWeight="bold"
        fill="#333"
      >
        {point.name}
      </text>
      <text
        x={10 / zoomScale}
        y={50 / zoomScale}
        fontSize={14 / zoomScale}
        fill="#555"
      >
        Оборудование: {point.equipmentAmount} устройств
      </text>

      {/* "Кнопка" */}
      <rect
        x={10 / zoomScale}
        y={70 / zoomScale}
        width={140 / zoomScale}
        height={24 / zoomScale}
        rx={6 / zoomScale}
        ry={6 / zoomScale}
        fill="#1a73e8"
        onClick={() => onDetailsClick?.(point.id)}
        style={{ cursor: 'pointer' }}
      />
      <text
        x={20 / zoomScale}
        y={86 / zoomScale}
        fontSize={13 / zoomScale}
        fill="#fff"
        style={{ pointerEvents: 'none' }}
      >
        Посмотреть детали
      </text>
    </g>
  );
};