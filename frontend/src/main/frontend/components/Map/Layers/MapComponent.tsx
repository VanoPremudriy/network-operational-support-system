// import React from 'react';
// import { MapContainer, TileLayer } from 'react-leaflet';
// import 'leaflet/dist/leaflet.css';
//
// const MapWithOSM = () => {
//   return (
//     <MapContainer
//       center={[51.505, -0.09]} // Центр карты
//       zoom={13} // Уровень масштаба
//       scrollWheelZoom={true}
//       style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
//     >
//       <TileLayer
//         url="https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"
//         attribution='&copy; <a href="https://carto.com/">CARTO</a>'
//       />
//     </MapContainer>
//   );
// };
//
// export default MapWithOSM;

import { MapContainer, TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const MapWithOSM = () => (
  <div style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}>
    <MapContainer
      center={[51.505, -0.09]}
      zoom={13}
      style={{ height: '100%', width: '100%' }}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; OpenStreetMap contributors"
      />
    </MapContainer>
  </div>
);

export default MapWithOSM;