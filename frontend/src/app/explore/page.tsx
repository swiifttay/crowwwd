"use client";

import FilterBar from "../components/Explore/FilterBar";
import FilterEvents from "../components/Explore/FilterEvents";
import "../globals.css";
import { TextField, Grid } from "@mui/material";

import { useState, useEffect } from "react";
import { concertsList as eventsList } from "../axios/apiService";
import { SearchBar } from "../components/Explore/SearchBar";


export interface Event {
  eventId: string;
  name: string;
  eventImageName: string;
  venue: string;
  categories: string[];
  artistName: string;
  dates: string[];
}

export default function Explore() {
  const [events, setEvents] = useState<Event[]>([]);
  const [query, setQuery] = useState("");
  const [selectedCats, setSelectedCats] = useState([]);
  const [selectedDateRange, setSelectedDateRange] = useState();

  //---------- Get Events ----------
  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    setEvents(await eventsList());
  };

  //--------- Search Filter ----------
  //set the state for Search
  const handleInputChange = (e) => {
    setQuery(e.target.value);
  };

  const queriedEvents = events.filter(
    (event) =>
      event.name.toLocaleLowerCase().indexOf(query.toLocaleLowerCase()) !== -1
  );

  //---------- Categories Filter ----------
  const handleCheckboxChange = (e) => {
    setSelectedCats(e.target.value);
  };

  //---------- Date Range Filter ----------
  const handleDateChange = (e) => {
    setSelectedDateRange(e.target.value);
  };

  //---------- Filter Results ----------
  const filteredResults = (
    queriedEvents,
    query,
    selectedCategory,
    selectedDateRange
  ) => {
    let filteredEvents = events;
    if (query) {
      filteredEvents = queriedEvents;
    }
    if (selectedCategory) {
      filteredEvents = filteredEvents.filter(({ categories }) =>
        categories.some((c) => selectedCategory.includes(c))
      );
    }

    return filteredEvents.map(({ name, eventImageName }) => {});
  };

  return (
    <main className="h-screen relative w-full bg-space bg-cover bg-center">

      <div id="title" className="flex px-9 flex-wrap items-center justify-center">
        <h1 className="flex-1 sticky mr-2 md:ml-10 py-10 text-center md:text-start font-bold text-5xl ">
          Latest Events
        </h1>
        <SearchBar />
      </div>

      <div id="event-items" className="flex flex-col items-center">
            <FilterBar
              onSelectCat={handleCheckboxChange}
              onSelectDateRange={handleDateChange}
              className="z-50"
            />
      </div>
    </main>
  );
}
