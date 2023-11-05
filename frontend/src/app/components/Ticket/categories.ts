import L from "leaflet";
import { LeafletEvent } from "leaflet";

export const lowerOpacity = (e:LeafletEvent) => e.target.setOpacity(0.7);

export const higherOpacity = (e:LeafletEvent) => e.target.setOpacity(1);

export const handleClick = (cat:string) => console.log(cat);

export const catA = new L.Icon({
    iconUrl: "/svg/catA.svg",
    iconSize: [200, 200],
    iconAnchor: [10, 10],
  });

export const catB = new L.Icon({
    iconUrl: "/svg/catB.svg",
    iconSize: [235, 230],
    iconAnchor: [10, 10],
  });

export const catC = new L.Icon({
    iconUrl: "/svg/catC.svg",
    iconSize: [160, 160],
    iconAnchor: [10, 10],
  });

export const catD = new L.Icon({
    iconUrl: "/svg/catD.svg",
    iconSize: [260, 260],
    iconAnchor: [10, 10],
  });

export const catE = new L.Icon({
    iconUrl: "/svg/catE.svg",
    iconSize: [290, 290],
    iconAnchor: [10, 10],
  });

export const catF = new L.Icon({
  iconUrl: "/svg/catF.svg",
  iconSize: [280, 270],
  iconAnchor: [10, 10],
});

export const stage = new L.Icon({
  iconUrl: "/svg/stage.svg",
  iconSize: [250, 250],
  iconAnchor: [10, 10],
});