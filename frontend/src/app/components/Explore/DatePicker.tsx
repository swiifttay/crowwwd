"use client";

import { useEffect, useRef, useState } from "react";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRange, RangeKeyDict } from "react-date-range";
import format from "date-fns/format";
import { addDays } from "date-fns";

interface Range {
  startDate: Date;
  endDate: Date;
  key: string;
}

interface DatePickerProps {
  onDateChange: (startDate: Date, endDate: Date) => void;
}

export default function DatePicker({ onDateChange }: DatePickerProps) {
  const [range, setRange] = useState<Range[]>([
    {
      startDate: new Date(),
      endDate: addDays(new Date(), 7),
      key: "selection",
    },
  ]);

  const [isOpen, setIsOpen] = useState(false);

  const refOne = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    document.addEventListener("keydown", hideOnEscape, true);
    document.addEventListener("click", hideOnClickOutside, true);
    return () => {
      document.removeEventListener("keydown", hideOnEscape, true);
      document.removeEventListener("click", hideOnClickOutside, true);
    }
  }, []);

  const hideOnEscape = (e: any) => {
    if (e.key === "Escape") {
      setIsOpen(false);
    }
  };

  const hideOnClickOutside = (e: any) => {
    if (refOne.current && !refOne.current.contains(e.target)) {
      setIsOpen(false);
    }
  };

  const handleDateSelectionChange = (ranges: RangeKeyDict) => {
    const selectedRange = ranges["selection"];
    if (selectedRange?.startDate && selectedRange?.endDate) {
      setRange([selectedRange as Range]);
      console.log()
      onDateChange(selectedRange.startDate, selectedRange.endDate);
    }
  }

  return (
    <div className="rounded-3xl bg-theme-blue text-sm py-2 px-4 text-center focus:ring-0">
      <input
        value={`${format(range[0].startDate, "MM/dd/yyyy")}  -  ${format(
          range[0].endDate,
          "MM/dd/yyyy"
        )}`}
        readOnly
        onClick={() => setIsOpen((isOpen) => !isOpen)}
        className="bg-transparent border-none focus:ring-0 h-full z-50"
      />

      <div ref={refOne} className="inline-block relative">
        {isOpen && (
          <DateRange
            onChange={handleDateSelectionChange}
            editableDateInputs={true}
            moveRangeOnFirstSelection={false}
            ranges={range}
            months={1}
            direction="horizontal"
            className="absolute right-full bg-none focus:ring-0"
          />
        )}
      </div>
    </div>
  );
}
