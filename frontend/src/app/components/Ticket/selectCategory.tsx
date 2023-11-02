"use client";

import { MapContainer, Marker, Popup } from "react-leaflet";
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
} from "./categories";

type SeatSelectParams = {
  selectCategory: (category: string) => void;
};

export function Seats({ selectCategory }: SeatSelectParams) {
  return (
    <MapContainer
      className="h-[26rem] w-full overflow-x-scroll z-30"
      center={[2, 0]}
      zoom={3.4}
      scrollWheelZoom={false}
      zoomControl={false}
      dragging={false}
    >
      <Marker
        key="A"
        position={[-6, 9]}
        icon={catA}
        eventHandlers={{
          mouseover: lowerOpacity,
          mouseout: higherOpacity,
          click: () => {
            selectCategory("A");
          },
        }}
      >
        <Popup>Cat A</Popup>
      </Marker>

      <Marker
        key="B"
        position={[7, -14]}
        icon={catB}
        eventHandlers={{
          mouseover: lowerOpacity,
          mouseout: higherOpacity,
          click: () => {
            selectCategory("B");
          },
        }}
      >
        <Popup>Cat B</Popup>
      </Marker>

      <Marker
        key="C"
        position={[15, 17]}
        icon={catC}
        eventHandlers={{
          mouseover: lowerOpacity,
          mouseout: higherOpacity,
          click: () => {
            selectCategory("C");
          },
        }}
      >
        <Popup>Cat C</Popup>
      </Marker>

      <Marker
        key="F"
        position={[23, 32]}
        icon={catF}
        eventHandlers={{
          mouseover: lowerOpacity,
          mouseout: higherOpacity,
          click: () => {
            selectCategory("F");
          },
        }}
      >
        <Popup>Cat F</Popup>
      </Marker>

      <Marker
        key="E"
        position={[21, -18]}
        icon={catE}
        eventHandlers={{
          mouseover: lowerOpacity,
          mouseout: higherOpacity,
          click: () => {
            selectCategory("E");
          },
        }}
      >
        <Popup>Cat E</Popup>
      </Marker>

      <Marker
        key="D"
        position={[-8, 33]}
        icon={catD}
        eventHandlers={{
          mouseover: lowerOpacity,
          mouseout: higherOpacity,
          click: () => {
            selectCategory("D");
          },
        }}
      >
        <Popup>Cat D</Popup>
      </Marker>

      <Marker position={[2, -50]} icon={stage} interactive={false} />
    </MapContainer>
  );
}
