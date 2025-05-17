import { Map } from 'Frontend/components/Map/Map';
import {useMapData } from 'Frontend/hooks/useMapData';
import { useRouteData } from 'Frontend/services/RouteService';

const RouteMap = ({ onPointClick }: { onPointClick: (id: string) => void }) => {

  const data = useMapData();
  const routeData = useRouteData();

  if (!data) {
    return <pre>Loading...</pre>;
  }

  if (!routeData || !routeData.points || !routeData.edges) {
    return <div>
      Невозможно отобразить карту.
    </div>
  }

  return (
    <Map
      data={data}
      points={routeData?.points ?? []}
      edges={routeData?.edges ?? []}
      onPointClick={onPointClick}
    />
  )
}

export default RouteMap;