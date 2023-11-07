"use client";

import React from "react"
import faqdata from "../../../../public/faqdata.json"
import { AccordionItem } from "../../components/FAQ/AccordionItem"

export default function BookingTickets() {
  const questions = faqdata[5].questions

  return (
    <main className="flex flex-col items-center h-fit relative w-full bg-space bg-cover bg-center px-8">
      <div
        id="title"
        className="flex flex-wrap w-full items-center justify-center px-3">
        <h1 className="flex-1 mr-2 py-10 text-center md:text-start text-6xl font-bold">Contact Us</h1>
      </div>
      <section className="flex flex-col items-justify w-full px-3 mb-10">
        <div>
          {questions.map((item) => (
              <AccordionItem
              question={item.question}
              answer={item.answer}/>
          ))}
        </div>
      </section>
    </main>
  )
}