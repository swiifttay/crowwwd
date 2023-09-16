import { Event } from "@/app/explore/page";
import { dateFormatter } from "@/app/utillities/dateFormat";
import { deflateSync } from "zlib";

export function Card(event: Event) {
  const startDate = event.dates[0];
  const endDate = event.dates[event.dates.length - 1];
  const displayedDate =
    startDate === endDate
      ? `${dateFormatter(startDate)}`
      : `${dateFormatter(startDate)} - ${dateFormatter(endDate)}`;

  return (
    <button className="w-full grid-rows-2 h-72 rounded-md transition-transform group text-left">
      <img
        src={event.eventImageURL}
        className="row-span-1 rounded-md h-40 object-cover w-full group-hover:opacity-60 group-hover:scale-105 group-hover:ease-in-out group-hover:duration-200"
      />
      <div className="row-span-1 pt-4">
        <h2 className="font-light text-lg">{event.name}</h2>
        <h3 className="text-xs text-slate-300">{displayedDate}</h3>
        {/* <button className="bg-theme-blue text-white px-2 py-2 rounded-full text-xs" /> */}
      </div>
    </button>
  );
}
