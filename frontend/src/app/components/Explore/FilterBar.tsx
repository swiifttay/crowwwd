"use client";

import { BsFilter } from "react-icons/bs";
import FilterCategory from "./FilterCategory";
import DatePicker from "react-datepicker";

import { useState } from "react";

export default function FilterBar() {
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date());

  return (
    <div className="h-full fixed mr-2 border-e-2 border-gray-500 px-12">
      <h1 className="py-3 justify-center flex flex-row text-base">
        Add Filters
        <BsFilter />
      </h1>
      <FilterCategory />
      <DatePicker
        selected={startDate}
        onChange={(date: Date | null) => {
          setStartDate(date);
        }}
      ></DatePicker>
      <DatePicker
        selected={endDate}
        onChange={(date: Date | null) => {
          setEndDate(date);
        }}
      ></DatePicker>
    </div>
  );
}
