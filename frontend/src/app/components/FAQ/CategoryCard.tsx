import React from "react";
import { IoArrowForwardCircle } from "react-icons/io5";
import { FaMoneyCheckDollar } from "react-icons/fa6";
import { RiQuestionnaireFill } from "react-icons/ri";
import { HiTicket } from "react-icons/hi2";
import { HiInformationCircle } from "react-icons/hi2";
import { MdAccountCircle } from "react-icons/md"
import { IoIosListBox } from "react-icons/io"


function RenderIcon({ category }) {
  if (category == "Payment") {
    return <FaMoneyCheckDollar className="text-2xl"/>
  } else if (category == "Booking Tickets"){
    return <HiTicket className="text-2xl"/>
  } else if (category == "Contact Us") {
    return <RiQuestionnaireFill className="text-2xl"/>
  } else if (category == "General Information") {
    return <HiInformationCircle className="text-2xl"/>
  } else if (category == "Account") {
    return <MdAccountCircle className="text-2xl"/>
  } else if (category == "Existing Bookings") {
    return <IoIosListBox className="text-2xl"/>
  }
}


export function CategoryCard({ key, category }) {
  const catString = category.toString()
  const link = catString.toLowerCase().replace(/\s/g, '')
  return (
    <a href={"/faq/"+link}>
      <button className="flex flex-row col-span-1 w-full h-28 rounded-md 
      bg-slate-700 items-center duration-300 hover:bg-slate-800 
      hover:drop-shadow-xl">
        <div className="px-5">
        <RenderIcon category={catString}/>
        </div>
        <div className="flex-none text-xl font-semibold">
          { category }
        </div>
        <div dir="rtl" className="flex-auto ms-5">
          <IoArrowForwardCircle className="text-2xl"/>
        </div>
      </button>
    </a>
  )
}