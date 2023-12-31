"use client";

import FilterBar from "../components/Explore/FilterBar";
import "../globals.css";

import { useState, useEffect, ChangeEvent } from "react";
import { getAllEvents } from "../axios/apiService";
import { SearchBar } from "../components/Explore/SearchBar";
import { Card } from "../components/Explore/Card";
import { BiLoaderAlt } from "react-icons/bi";

export interface Venue {
  address: string;
  description: string;
  id: string;
  locationName: string;
  postalCode: string;
  venueImageName: string;
}

export interface Event {
  eventId: string;
  name: string;
  eventImageURL: string;
  eventImageName: string;
  venue: Venue;
  categories: string[];
  artistName: string;
  dates: string[];
  description: string;
}

export default function Explore() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [events, setEvents] = useState<Event[]>([]);
  const [query, setQuery] = useState("");
  const [selectedCat, setSelectedCat] = useState<string[]>([]);
  const [dateRange, setDateRange] = useState<{
    startDate: Date;
    endDate: Date;
  } | null>(null);

  //---------- Get Events ----------
  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    // check if response valid
    const data = await getAllEvents();
    setEvents(data.exploreEventList);
    setIsLoaded(true);
  };

  //--------- Search Filter ----------
  const handleInputChange = (input: string) => {
    setQuery(input.toLocaleLowerCase());
    setSelectedCat([]);
    setDateRange(null);
  };

  const queriedEvents = events?.filter(
    (event) =>
      event.name.toLocaleLowerCase().indexOf(query) !== -1 ||
      event.artistName.toLocaleLowerCase().indexOf(query) !== -1,
  );

  //---------- Set Selected Categories ----------
  const handleCheckboxChange = (e: ChangeEvent<HTMLInputElement>) => {
    console.log(e.target.value);
    let cat = e.target.value;
    if (!selectedCat.includes(cat.toLocaleLowerCase()) && e.target.checked) {
      setSelectedCat((prev) => [...prev, cat.toLocaleLowerCase()]);
    } else if (
      selectedCat.includes(cat.toLocaleLowerCase()) &&
      !e.target.checked
    ) {
      setSelectedCat(selectedCat.filter((value) => cat !== value));
    }
  };

  //---------- Set Selected Date Range ----------
  const handleDateChange = (startDate: Date, endDate: Date) => {
    setDateRange({ startDate, endDate });
  };

  //---------- Filter Results ----------
  const filteredResults = (
    queriedEvents: Event[],
    query: string,
    selectedCat: string[],
    dateRange: { startDate: Date; endDate: Date } | null,
  ) => {
    let filteredEvents = events;
    if (query !== "") {
      filteredEvents = queriedEvents;
    }
    if (selectedCat && selectedCat.length > 0) {
      filteredEvents = filteredEvents?.filter(({ categories }) =>
        categories.some((c) => {
          return selectedCat.includes(c.toLocaleLowerCase());
        }),
      );
    }
    if (dateRange) {
      filteredEvents = filteredEvents.filter(({ dates }) => {
        return dates.some((d) => {
          const date = new Date(d);
          return date >= dateRange.startDate && date <= dateRange.endDate;
        });
      });
    }

    return filteredEvents?.map((event: Event) => (
      <div
        key={event.eventId}
        className="w-full md:col-span-4 sm:col-span-6 col-span-12"
      >
        <Card {...event} />
      </div>
    ));
  };

  const displayedItems = filteredResults(
    queriedEvents,
    query,
    selectedCat,
    dateRange,
  );

  return (
    <main className="flex flex-col items-center h-fit relative w-full bg-space bg-cover bg-center px-8">
      <div
        id="title"
        className="flex flex-wrap w-full items-center justify-center px-3"
      >
        <h1 className="flex-1 sticky mr-2 py-10 text-center md:text-start font-bold text-6xl">
          Latest Events
        </h1>
        <SearchBar onInput={handleInputChange} />
      </div>

      <FilterBar
        onCheck={handleCheckboxChange}
        onDateChange={handleDateChange}
      />

      {isLoaded ? (
        <div className="w-full px-3 grid md:grid-cols-12 gap-5">
          {displayedItems}
        </div>
      ) : (
        <div className="my-5 w-full flex text-2xl text-center font-bold justify-center">
          <div className="px-3">Loading</div>{" "}
          <BiLoaderAlt className="animate-spin" />
        </div>
      )}
    </main>
  );
}
