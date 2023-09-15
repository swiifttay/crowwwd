"use client";

import { BsFilter } from "react-icons/bs";
import FilterCategory from "./FilterCategory";
import DatePicker from "./DatePicker";

import { ChangeEvent, RefObject, useEffect, useRef, useState } from "react";

function useOutsideAlerter(ref: RefObject<HTMLElement>) {}

export default function FilterBar() {
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date());

  const [isOpen, setIsOpen] = useState(false);

  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    document.addEventListener("keydown", hideOnEscape, true);
    document.addEventListener("click", hideOnClickOutside, true);
    return () => {
      document.removeEventListener("keydown", hideOnEscape, true);
      document.removeEventListener("click", hideOnClickOutside, true);
    };
  }, []);

  const hideOnEscape = (e: any) => {
    if (e.key === "Escape") {
      setIsOpen(false);
    }
  };

  const hideOnClickOutside = (e: any) => {
    if (wrapperRef.current && !wrapperRef.current.contains(e.target)) {
      setIsOpen(false);
    }
  };

  return (
    <div className="flex w-11/12 md:w-5/6 my-3 py-3 items-center justify-between">
      <div ref={wrapperRef} className="flex relative">
        <button

          onClick={() => {
            setIsOpen(!isOpen);
          }}
          className="flex py-2 px-4 bg-red-600 rounded-full text-sm hover:ease-in-out duration-300 hover:-translate-y-1 hover:scale-105"
        >
          <BsFilter className="text-lg" /> Select Category
        </button>
        {isOpen && (
          <div className="w-2 h-10 top-10 left-5 absolute">
            <FilterCategory />
          </div>
        )}
      </div>
      <DatePicker />
    </div>
  );
}
