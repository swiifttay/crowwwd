"use client";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { concertDetails } from "../../axios/apiService";
import { StringLiteral } from "typescript";

export interface Venue {
  venueId: string;
  locationName: string;
  address: string;
  postalCode: string;
  description: string;
}

export interface Event {
  eventId: string;
  name: string;
  eventImageURL: string;
  dates: string[];
  ticketSalesDate: string[];
  categories: string[];
  venue: Venue;
  description: string;
}

export default function EventDetails({ params }: any) {
  const router = useRouter();
  const { eventId } = params;
  const [eventDetail, setEventDetail] = useState<Event>();

  const rulesHeader = "font-bold text-md ml-8 mb-2 mt-4";
  const rulesDetails = "list-disc ml-16";

  useEffect(() => {
    fetchDetails();
  }, []);
  // useEffect(() => {
  //   if (eventDetail === null) {
  //     fetchDetails();
  //   }
  // }, []);

  const handleBuyTickets = async () => {
    router.push("/order");
  };

  const fetchDetails = async () => {
    try {
      const response = await concertDetails(eventId);
      setEventDetail(response.data.detailsEvent);
    } catch (error) {
      console.error(error);
      router.push("/pageNotFound");
    }
  };

  console.log("eventDetail is", eventDetail);

  return (
    <div className="flex w-full items-center justify-center mt-4">
      <main className="w-11/12">
        <div className="w-full h-1/2">
          <img
            src={eventDetail?.eventImageURL}
            className="max-h-1/2 rounded-3xl cover"
          />
        </div>

        <div className="max-w-[1045px] mt-8">
          <div className="text-3xl font-bold mb-4">{eventDetail?.name}</div>

          <div className="flex justify-between">
            <p className="text-lg mb-10">
              {eventDetail?.categories.map((category: string, index: any) => (
                <span key={index}>
                  {category}
                  {index < eventDetail?.categories.length - 1 && " | "}
                </span>
              ))}
            </p>
            <button
              className="w-[150px] h-1/6 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded -mt-4"
              onClick={handleBuyTickets}
            >
              Buy Tickets
            </button>
          </div>

          <div className="flex ml-10">
            <Image
              src="/images/icon-calendar.svg"
              alt="Calendar"
              width={20}
              height={20}
            />

            <div className="flex ml-3 flex-wrap gap-3 ">
              {eventDetail?.dates.map((date: string, index: any) => (
                <div className="text-md mr-20 hover:text-theme-grey hover:underline cursor-pointer">
                  {date}
                </div>
              ))}
            </div>
          </div>
          <div className="flex gap-3 ml-10 mt-3">
            <Image
              src="/images/icon-map-pin.svg"
              alt="Location"
              width={20}
              height={20}
            />
            <div className="text-md mr-20 hover:text-theme-grey hover:underline cursor-pointer">
              {eventDetail?.venue?.locationName}
            </div>
          </div>

          <p className="text-md mt-10 mb-10">{eventDetail?.description}</p>

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
              There will be no admission into the venue once the performance has
              commenced. Admission may only be permitted during a suitable
              pause, depending on the nature of the performance
            </li>
          </ul>

          <div className="mt-12 text-xl font-bold mb-2">FAQ</div>
          <div className="flex gap-1 mb-16">
            <div>Click</div>
            <div className="hover:underline text-theme-light-blue cursor-pointer">
              here
            </div>
            <div>to view the FAQ for this event.</div>
          </div>
        </div>
      </main>
    </div>
  );
}
