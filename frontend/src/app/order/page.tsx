"use client";
import { useState } from "react";
import { BsFillCalendarFill } from "react-icons/bs";
import { FaLocationDot } from "react-icons/fa6";
import { IoTicket } from "react-icons/io5";

export default function Order() {
  return (
    <main className="w-full py-10 px-20">
      <div className="h-96 mb-10 rounded-3xl bg-azure bg-cover bg-center flex flex-col justify-center items-center shadow-lg shadow-slate-950">
        <h1 className="m-2 text-5xl font-semibold text-center">
          You Got The Tickets!
        </h1>
        <h3>Order #-3JLCK34-34UI</h3>
      </div>
      <section className="grid grid-rows-6 grid-flow-col gap-8">
        <img
          src="/images/jaychou.jpg"
          className="row-span-full col-span-2 w-full h-60 rounded-xl object-cover"
        />
        <div className="col-span-5 row-span-3">
          <h2 className="text-xl font-semibold mb-3">Event Title</h2>
          <h3 className="flex row-span-1 text-sm font-light text-center mb-2">
            <BsFillCalendarFill className="me-3" /> Event Date
          </h3>
          <h3 className="flex row-span-1 text-sm font-light text-center mb-2">
            <FaLocationDot className="me-3" /> Event Location
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
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer
          ornare pulvinar velit eu luctus. Curabitur sed sem et tortor dictum
          tempor. Nunc non iaculis elit, at sollicitudin arcu. Duis vel lacus
          ultricies, facilisis ipsum ut, tincidunt orci. Donec tempor eros vel
          metus aliquam, ut hendrerit urna porta. Cras tincidunt ex dui, sed
          sodales est porta non. Nulla vitae dignissim arcu. Integer eget rutrum
          ipsum. Maecenas vitae vehicula sem. Pellentesque elementum maximus
          erat. Etiam ac neque et elit commodo auctor non eget purus.
          Pellentesque ac dui sapien. Nunc laoreet quis ligula at interdum.
        </p>
      </section>
    </main>
  );
}
