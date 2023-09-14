"use client";

import { BsFilter } from "react-icons/bs";
import FilterCategory from "./FilterCategory";
import DatePicker from "./DatePicker";

import { useState } from "react";

export default function FilterBar({handelCheckboxChange, handleDateChange}) {

  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date());

  return (
    <div className="h-full mr-2 border-e-2 border-gray-500 px-4 w-fit">
      <h1 className="py-3 justify-center flex flex-row text-base">
        Add Filters
        <BsFilter />
      </h1>
      <FilterCategory />
      <DatePicker />


    </div>
  );
}
