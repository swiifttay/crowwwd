"use client";
import { useEffect, useRef, useState } from "react";
import { Seats } from "../../components/Ticket/selectCategory";
import { getCategoryPrice, getEvent } from "../../axios/apiService";
import { Event } from "@/app/explore/page";
import { CatPriceDisplay } from "../../components/Ticket/CatPriceDisplay";

type CategoryAndPrice = {
  category: string;
  price: number;
  availableSeats: number;
}

export default function Ticket({ params }: { params: { eventId: string } }) {
  const { eventId } = params;

  const [category, setCategory] = useState<CategoryAndPrice>();
  const [event, setEvent] = useState<Event>();
  const [prices, setPrices] = useState<CategoryAndPrice[]>([]);
  const [numSeatsPurchasable, setNumSeatsPurchasable] = useState(0);
  const [renderedNumSeats, setRenderedNumSeats] = useState<JSX.Element[]>([]);
  const ticketRef = useRef<HTMLSelectElement | null>(null);


  useEffect(() => {
    //fetch the price of the event. Called inside fetchEvent
    const fetchPrices = async () => {
      const res = await getCategoryPrice(eventId);
      //console.log(res);
      setPrices(res.seatingDetails.categories);
    };

    //fetch the event based on eventId. Then calls fetchPrices.
    const fetchEvent = () => {
      getEvent(eventId)
        .then((event) => {
          //console.log(event);
          setEvent(event.fullEvent as Event);
        })
        .then(fetchPrices);
    };
    //Call fetchEvent();
    fetchEvent();
  }, []);

  //For effect of scrolling to ticket details once category selected
  useEffect(() => {
    ticketRef.current?.scrollIntoView({ behavior: "smooth", block: "center" });
  });

  //Change seat quntity available for selection by user
  useEffect(() => {
    const availableSeatsDefined = category?.availableSeats ?? 0;
    const numSeatsAvailable = (category?.availableSeats ?? 0) > 4 ? 4 : availableSeatsDefined;
    renderNumSeatsSelection(numSeatsAvailable);
  },[category]);

  //Event handler for category selection
  function handleSelect(selected: string) {
    setCategory(prices.find((cat) => cat.category === selected) ?? undefined);
  }

  function renderNumSeatsSelection(numSeats: number) {
    const list = Array.from({length: numSeats}, (_, index) => index + 1)
    setRenderedNumSeats(list.map((num) => <option key={num}>{num}</option>));
  }

  return (
    <main className="w-11/12 mx-auto">
      <header className="flex w-full">
        <img src={event?.eventImageURL} className="object-cover object-center w-1/4 h-20" />
        <div>
          <h3 className="text-xl">{event?.artistName} {event?.name}</h3>
          <h5 className="rounded-full bg-theme-blue-30 px-3 py-1">{`${event?.dates[0]} ${event?.venue}`}</h5>
        </div>
      </header>

      <figure className="w-full my-16 relative flex justify-center items-center bg-gradient-to-r from-theme-accent to-theme-blue-40 h-[32rem] rounded-[4rem] shadow-lg shadow-zinc-900">
        <figcaption className="absolute left-0 top-0 p-16 hidden md:block">
          <h1 className="font-black text-4xl">Select Category</h1>
          <h3 className="font-bold text-zinc-300">View seat availabilities</h3>
        </figcaption>
        <Seats selectCategory={handleSelect} />
      </figure>

      {prices && 
      <figcaption className="flex mb-20">
        <div className="w-fit px-10 grid grid-rows-6">
          <CatPriceDisplay cat={prices[0]?.category} price={prices[0]?.price} colour="text-[#7C87BE]" />
          <CatPriceDisplay cat={prices[1]?.category} price={prices[1]?.price} colour="text-[#7C87BE]" />
          <CatPriceDisplay cat={prices[2]?.category} price={prices[2]?.price} colour="text-[#9CCAEB]" />
          <CatPriceDisplay cat={prices[3]?.category} price={prices[3]?.price} colour="text-[#F4C47E]" />
          <CatPriceDisplay cat={prices[4]?.category} price={prices[4]?.price} colour="text-[#F4C47E]" />
          <CatPriceDisplay cat={prices[5]?.category} price={prices[5]?.price} colour="text-[#425BA4]" />
        </div>
        <ul className="px-10 list-disc text-sm font-light [&>li]:my-1">
          <li>Seat Plan is not drawn to Scale</li>
          <li>Colour indicates price cateogry</li>
          <li>Ticket prices exclude booking fees</li>
          <li>Entry prerequisites: <span className="font-bold">LA4CS</span></li>
        </ul>
      </figcaption>}

      {category && (
        <section
          ref={ticketRef}
          className="w-5/6 my-10 grid grid-rows-2 text-center align-middle"
        >
          <div className="row-span-1 bg-theme-blue-40 grid grid-cols-6 text-center p-3">
            <div className="col-span-2 font-white">
              Selected Category
            </div>

            <div className="col-span-1 font-white">Seat Price</div>

            <div className="col-span-1 font-white">Quantity</div>
            <div className="col-span-2 font-white">Ticket Info</div>
          </div>

          <div className="row-span-1 grid grid-cols-6">
            <div className="col-span-2 border border-theme-grey font-bold flex justify-center items-center">
              {category?.category}
            </div>
            <div className="col-span-1 border border-theme-grey font-bold flex justify-center items-center">
              ${category?.price}
            </div>
            <div className="col-span-1 border border-theme-grey p-3">
              <select className="bg-theme-blue-50 rounded-md border border-theme-light-blue w-full text-sm" defaultValue="default" >
                <option value="default">Select Qty</option>
                {renderedNumSeats}
              </select>
            </div>
            <div className="col-span-2 border border-theme-grey"></div>
          </div>
        </section>
      )}
    </main>
  );
}
