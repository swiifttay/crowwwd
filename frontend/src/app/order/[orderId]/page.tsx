"use client";

import { fetchOrderByOrderId, getEvent } from "@/app/axios/apiService";
import { Event } from "@/app/explore/page";
import { Order } from "@/app/payment/[orderId]/page";
import { useEffect, useState } from "react";
import { BsFillCalendarFill } from "react-icons/bs";
import { FaLocationDot } from "react-icons/fa6";
import { IoTicket } from "react-icons/io5";
import { dateFormatter } from "@/app/utillities/dateFormat";

export default function Order({ params }: { params: { orderId: string } }) {
  const { orderId } = params
  const [order, setOrder] = useState<Order>();
  const [event, setEvent] = useState<Event>();
  
  useEffect(() => {
    const fetchOrderAndThenEvent = async () => {
      try {
        // Fetch the order first
        const orderResponse = await fetchOrderByOrderId(orderId);
        const fetchedOrder = orderResponse.data.order;
        setOrder(fetchedOrder);
  
        if (typeof fetchedOrder?.eventId === 'string') {
          const eventResponse = await getEvent(fetchedOrder.eventId);
          setEvent(eventResponse.fullEvent); 
          console.log(event)
        }
        console.log(event?.eventImageURL)
      } catch (error) {

        console.error(error);
      }
    };
  
    fetchOrderAndThenEvent();
  }, [orderId]); 
  
  
  function formatDate (date: string){
    return dateFormatter(date);
  }
  

  return (
    <main className="w-full py-10 px-20">
      <div className="h-96 mb-10 rounded-3xl bg-azure bg-cover bg-center flex flex-col justify-center items-center shadow-lg shadow-slate-950">
        <h1 className="m-2 text-5xl font-semibold text-center">
          You Got The Tickets!
        </h1>
        <h3>{`Order #${orderId}`}</h3>
      </div>
      <section className="grid grid-rows-6 grid-flow-col gap-8">
        <img
              src={event?.eventImageURL}
              alt={event?.eventImageName}
              className="row-span-full col-span-2 w-full h-60 rounded-xl object-cover"
              width={1045}
              height={487}
            />
        <div className="col-span-5 row-span-3">
          <h2 className="text-xl font-semibold mb-3">{event?.name}</h2>
          <h3 className="flex row-span-1 text-sm font-light text-center mb-2">
            <BsFillCalendarFill className="me-3" /> {formatDate(order?.eventDate)}
          </h3>
          <h3 className="flex row-span-1 text-sm font-light text-center mb-2">
            <FaLocationDot className="me-3" /> {event?.venue.locationName}
          </h3>
        </div>
        <div className="row-span-3">
          <h2 className="text-xl font-semibold mb-3">Ticket Details</h2>
          <h3 className="flex row-span-1 text-sm font-light text-center mb-2">
            <IoTicket className="me-3" /> What tickets they got
          </h3>
        </div>
      </section>
      <section className="mt-5">
        <h2 className="my-2 text-2xl font-light text-theme-light-blue">
          Notes
        </h2>
        <p className="text-sm font-thin text-slate-400">
          Tickets sold are not refundable
        </p>
      </section>
    </main>
  );
}
