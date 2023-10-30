import L from "leaflet";
import { LeafletEvent } from "leaflet";

export const lowerOpacity = (e:LeafletEvent) => {e.target.setOpacity(0.7); e.target.openPopup()}

export const higherOpacity = (e:LeafletEvent) => {e.target.setOpacity(1);}

export const handleClick = (cat: string) => console.log(cat);

export const catA = new L.Icon({
    iconUrl: "/svg/catA.svg", 
    iconSize: [200, 200], 
    iconAnchor: [100, 100], 
  });

export const catB = new L.Icon({
    iconUrl: "/svg/catB.svg", 
    iconSize: [235, 230], 
    iconAnchor: [115, 115], 
  });

export const catC = new L.Icon({
    iconUrl: "/svg/catC.svg", 
    iconSize: [160, 160], 
    iconAnchor: [70, 80], 
  });

export const catD = new L.Icon({
    iconUrl: "/svg/catD.svg",
    iconSize: [260, 260], 
    iconAnchor: [130, 130], 
  });

export const catE = new L.Icon({
    iconUrl: "/svg/catE.svg", 
    iconSize: [290, 290], 
    iconAnchor: [145, 145], 
  });
  
export const catF = new L.Icon({
  iconUrl: "/svg/catF.svg",
  iconSize: [280, 270],
  iconAnchor: [140, 135],
});

export const stage = new L.Icon({
  iconUrl: "/svg/stage.svg",
  iconSize: [250, 250],
  iconAnchor: [10, 10],
});
