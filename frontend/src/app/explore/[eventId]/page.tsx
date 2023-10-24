"use client";

import { getEvent } from "@/app/axios/apiService";
import Image from "next/image";
import { useEffect, useState } from "react";
import { Event } from "../page";
import { dateFormatter } from "@/app/utillities/dateFormat";

type EventParams = {
  params: {
    eventId: string;
  };
};

export default function Event({ params: { eventId } }: EventParams) {
  const rulesHeader = "font-bold text-md ml-8 mb-2 mt-4";
  const rulesDetails = "list-disc ml-16";

  const [event, setEvent] = useState<Event | null>(null);

  useEffect(() => {
    fetchEventDetails();
  }, []);

  const fetchEventDetails = async () => {
    console.log("hi");
    const data = await getEvent(eventId);
    setEvent(data);
  };

  console.log(event);
  const startDate = event?.dates[0];
  console.log(dateFormatter(startDate))
  const endDate = event?.dates[event?.dates.length - 1];
  const pleaseWork = () => {
    if (startDate && endDate) {
      return (startDate === endDate
        ? dateFormatter(startDate)
        : `${dateFormatter(startDate)} - ${dateFormatter(endDate)}`);
    } else {
      return "";
    }
  };
  const displayedDate = pleaseWork();

  return (
    <div className="flex flex-col justify-center items-center">
      { event &&
        <div className="w-auto mt-32">
          <div className="">
            <img
              src={event?.eventImageURL}
              alt={event.eventImageName}
              className="rounded-3xl"
              width={1045}
              height={487}
            />
          </div>

          <div className="max-w-[1045px] mt-8">
            <div className="text-3xl font-bold mb-4">{event?.name}</div>

            <div className="flex justify-between">
              <p className="text-lg mb-10">KPOP | CONCERT</p>
              <button className="w-[150px] h-1/6 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded -mt-4">
                Buy Tickets
              </button>
            </div>

            <div className="flex gap-3 ml-10">
              <Image
              src="/images/icon-calendar.svg"
              alt="Calendar"
              width={20}
              height={20}
            />
              <div className="text-md mr-20 hover:text-theme-grey hover:underline cursor-pointer">
                {displayedDate}
              </div>
              <Image
                src="/images/icon-map-pin.svg"
              alt="Calendar"
              width={20}
              height={20}
            />
              <div className="text-md mr-20 hover:text-theme-grey hover:underline cursor-pointer">
                {event?.venue}
              </div>
            </div>

            <p className="text-md mt-10 mb-10">{event?.description}</p>

            <div className="font-semibold text-2xl mb-4">Price Details</div>
            <div className="grid text-md gap-2">
              <div>VIP: $328-$1228 </div>
              <div>CAT 1-3: $288-$348 </div>
              <div>CAT 4-6: $108-$248 </div>
              <div>CAT 7-9: $88-$248 </div>
              <div className="italic mt-6">
                *Ticketing price excludes booking charge
              </div>
              <div className="italic">
                *All tickets include a souvenir show card, official poster,
                special packet and good-bye session
              </div>
            </div>

            <button className="mt-10 w-[200px] h-1/6 border-2 hover:bg-neutral-600 text-white font-bold py-2 px-4 rounded">
              Seat Map
            </button>

            <div className="mt-12 text-xl font-bold mb-2">Admission Rules</div>
            <div className={rulesHeader}>Rating / Age Limit</div>
            <ul className={rulesDetails}>
              <li>Rating: General</li>
              <li>
                Children under 12 must be accompanied by an adult with a ticket
                for the same section
              </li>
              <li>Admission is subject to tickets produced at the entrance</li>
            </ul>
            <div className={rulesHeader}>Late Seating Advisory</div>
            <ul className={rulesDetails}>
              <li>
                Please be seated 15 minutes before the performance start time.
                There will be no admission into the venue once the performance
                has commenced. Admission may only be permitted during a suitable
                pause, depending on the nature of the performance
              </li>
            </ul>

            <div className="mt-12 text-xl font-bold mb-2">FAQ</div>
            <div className="flex gap-1">
              <div>Click</div>
              <div className="hover:underline text-theme-light-blue cursor-pointer">
                here
              </div>
              <div>to view the FAQ for this event.</div>
            </div>
          </div>
        </div>
      }
    </div>
  );
}
