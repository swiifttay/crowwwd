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
    iconSize: [170, 100], 
    iconAnchor: [10, 10], 
  });

export const catD = new L.Icon({
    iconUrl: "/svg/catD.svg",
    iconSize: [370, 300], 
    iconAnchor: [10, 10], 
  });

export const catE = new L.Icon({
    iconUrl: "/svg/catE.svg", 
    iconSize: [450, 300], 
    iconAnchor: [10, 10], 
  });

export const catF = new L.Icon({
  iconUrl: "/svg/catF.svg",
  iconSize: [390, 390],
  iconAnchor: [10, 10],
});

export const stage = new L.Icon({
  iconUrl: "/svg/stage.svg",
  iconSize: [250, 250],
  iconAnchor: [10, 10],
});