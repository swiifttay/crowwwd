"use client";
import { useEffect, useRef, useState } from "react";
import { Seats } from "../../components/Ticket/selectCategory";
import { getCategoryPrice, getEvent } from "../../axios/apiService";
import { Event } from "@/app/explore/page";

export default function Ticket({ params }: { params: { eventId: string } }) {
  const { eventId } = params;
  const [category, setCategory] = useState<string>("");
  const [event, setEvent] = useState<Event>();
  const [prices, setPrices] = useState();
  const ticketRef = useRef<HTMLSelectElement | null>(null);

  useEffect(() => {
    //fetch the price of the event. Called inside fetchEvent
    const fetchPrices = async () => {
      const res = await getCategoryPrice(eventId);
      setPrices(res);
    };

    //fetch the event based on eventId. Then calls fetchPrices.
    const fetchEvent = () => {
      getEvent(eventId).then((event: Event) => {
        setEvent(event);
      }).then(fetchPrices);
    }
    //Call fetchEvent();
    fetchEvent();
  }, []);

  //For effect of scrolling to ticket details once category selected
  useEffect(() => {
    ticketRef.current?.scrollIntoView({ behavior: "smooth", block: "center" });
  });

  //Event handler for category selection
  function handleSelect(category: string) {
    console.log("category selected is: " + category);
    setCategory(category);
  }

  return (
    <main className="w-full">
      <figure className="w-11/12 mx-auto my-20 relative flex justify-center items-center bg-gradient-to-r from-theme-accent to-theme-blue-40 h-[32rem] rounded-[4rem] shadow-lg shadow-zinc-900">
        <figcaption className="absolute left-0 top-0 p-16 hidden md:block">
          <h1 className="font-black text-4xl">Select Category</h1>
          <h3 className="font-bold text-zinc-300">View seat availabilities</h3>
        </figcaption>
        <Seats selectCategory={handleSelect} />
      </figure>

      {category != "" && (
        <section
          ref={ticketRef}
          className="w-5/6 my-10 grid grid-rows-2 mx-auto"
        >
          <div className="row-span-1 bg-theme-blue-40 grid grid-cols-6 text-center p-3">
            <div className="col-span-2 font-white text-lg">
              Selected Category
            </div>

            <div className="col-span-1 font-white text-lg">Seat Price</div>

            <div className="col-span-1 font-white text-lg">Quantity</div>
            <div className="col-span-2 font-white text-lg">Ticket Info</div>
          </div>

          <div className="row-span-1 grid grid-cols-6">
            <div className="col-span-2 border border-theme-grey font-bold flex justify-center items-center">
              {category}
            </div>
            <div className="col-span-1 border border-theme-grey font-bold flex justify-center items-center">
              {}
            </div>
            <div className="col-span-1 border border-theme-grey p-3">
              <select className="bg-theme-blue-50 rounded-md border border-theme-light-blue w-full">
                {/*Need to call api to check how many seats are left*/}
                <option>1</option>
                <option>2</option>
                <option>3</option>
              </select>
            </div>
            <div className="col-span-2 border border-theme-grey"></div>
          </div>
        </section>
      )}
      {/* {category !== "" && <div></div>} */}
    </main>
  );
}
