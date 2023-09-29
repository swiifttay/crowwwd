import { useState } from "react";
import { CategoryCard } from "../components/FAQ/CategoryCard";
import faqdata from "../../../public/faqdata.json";



export default function FAQ() {
  const data = faqdata

  return (
    <main className="flex flex-col items-center h-fit relative w-full bg-space bg-cover bg-center px-8">
      <div
        id="title"
        className="flex flex-wrap w-full items-center justify-center px-3">
        <h1 className="flex-1 mr-2 py-10 text-center md:text-start text-6xl font-bold">How can we help?</h1>
      </div>
      <section className="flex flex-col items-justify w-full px-3 mb-10">
        <div className="grid grid-cols-3 gap-6">
          {data.map((category, index) => (
            <CategoryCard key={index} category={category.category} />
          ))}
        </div>
      </section>
    </main> 
  )
}

