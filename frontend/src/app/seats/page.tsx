"use client";

import { MapContainer, Marker } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import "./Seats.css";
import {
  catA,
  catB,
  catC,
  catD,
  catE,
  catF,
  higherOpacity,
  lowerOpacity,
  stage,
} from "../components/Seats/category";

export default function Seats() {
  return (
    <MapContainer
      className="h-[32rem] w-full"
      center={[0, 0]}
      zoom={3.4}
      scrollWheelZoom={false}
      zoomControl={false}
      dragging={false}
    >
      <Marker
        position={[9, -5]}
        icon={catA}
        eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
      />
      <Marker
        position={[24, -32]}
        icon={catB}
        eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
      />
      <Marker
        position={[23, 15]}
        icon={catC}
        eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
      />
      {/*
      <Marker
        position={[45, 17]}
        icon={catF}
        eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
      />
      <Marker
        position={[36, -58]}
        icon={catE}
        eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
      />
      <Marker
        position={[1, 22]}
        icon={catD}
        eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
  />*/}
      <Marker position={[2, -50]} icon={stage} interactive={false} />
    </MapContainer>
  );
}
{
  /* <Marker
position={[3, -4.8]}
icon={catA}
eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
/>
<Marker
position={[20, -40]}
icon={catB}
eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
/>
<Marker
position={[23, 15]}
icon={catC}
eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
/>
<Marker
position={[45, 17]}
icon={catF}
eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
/>
<Marker
position={[36, -58]}
icon={catE}
eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
/>
<Marker
position={[1, 22]}
icon={catD}
eventHandlers={{ mouseover: lowerOpacity, mouseout: higherOpacity }}
/>
<Marker
position={[-5.7, -67]}
icon={stage}
/> */
}
