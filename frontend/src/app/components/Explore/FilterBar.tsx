"use client";

import { BsFilter } from "react-icons/bs";
import FilterCategory from "./FilterCategory";
import DatePicker from "./DatePicker";

import { RefObject, useEffect, useRef, useState, ChangeEvent } from "react";

// function useOutsideAlerter(ref: RefObject<HTMLElement>) {}

type SelectProps = {
  onCheck: (input: ChangeEvent<HTMLInputElement>) => void;
  onDateChange: (startDate: Date, endDate: Date) => void;
};

export default function FilterBar({ onCheck, onDateChange }: SelectProps) {
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
    <div className="flex px-3 w-full mt-4 items-center justify-between">
      <div ref={wrapperRef} className="flex relative">
        <button
          onClick={() => {
            setIsOpen(!isOpen);
          }}
          className="flex py-2 px-4 bg-red-600 rounded-full text-sm hover:ease-in-out duration-300 hover:-translate-y-1 hover:scale-105 z-20"
        >
          <BsFilter className="text-lg" /> Select Category
        </button>
        {isOpen && (
          <div className="h-10 w-full top-5 absolute z-10">
            <FilterCategory onCheck={onCheck} />
          </div>
        )}
      </div>
      <DatePicker onDateChange={onDateChange} />
    </div>
  );
}
