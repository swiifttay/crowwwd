"use client";

import FilterBar from "../components/Explore/FilterBar";
import FilterEvents from "../components/Explore/FilterEvents";
import "../globals.css";

import { useState, useEffect, ChangeEvent, SyntheticEvent } from "react";
import { concertsList } from "../axios/apiService";
import { SearchBar } from "../components/Explore/SearchBar";
import { Card } from "../components/Explore/Card";
import { Grid } from "@mui/material";
import { setDate } from "date-fns";

export interface Event {
  eventId: string;
  name: string;
  eventImageURL: string;
  venue: string | null;
  categories: string[];
  artist: { name: string };
  dates: string[];
}

export default function Explore() {
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
    const eventList: Event[] = await concertsList();
    console.log(eventList);
    setEvents(eventList);
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
      event.artist.name.toLocaleLowerCase().indexOf(query) !== -1
  );

  //---------- Categories Filter ----------
  const handleCheckboxChange = (e: ChangeEvent<HTMLInputElement>) => {
    console.log(e.target.value)
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

  //---------- Date Range Filter ----------
  const handleDateChange = (startDate: Date, endDate: Date) => {
    setDateRange({ startDate, endDate });
  };

  //---------- Filter Results ----------
  const filteredResults = (
    queriedEvents: Event[],
    query: string,
    selectedCat: string[],
    dateRange: { startDate: Date; endDate: Date } | null
  ) => {
    let filteredEvents = events;
    if (query !== "") {
      filteredEvents = queriedEvents;
    }
    if (selectedCat && selectedCat.length > 0) {
      console.log(selectedCat)
      filteredEvents = filteredEvents?.filter(({ categories }) =>
        categories.some((c) => {
          console.log(c.toLocaleLowerCase());
          return selectedCat.includes(c.toLocaleLowerCase())})
      );
    }
    if (dateRange) {
      filteredEvents = filteredEvents.filter(({ dates }) => {
        dates.some((d) => {
          const date = new Date(d);
          return date >= dateRange.startDate && date <= dateRange.endDate;
        });
      });
    }

    return filteredEvents?.map((event: Event) => (
      <Grid key={event.eventId} item lg={4} md={4} sm={6} className="w-full">
        <Card {...event} />
      </Grid>
    ));
  };

  const displayedItems = filteredResults(
    queriedEvents,
    query,
    selectedCat,
    dateRange
  );

  return (
    <main className="flex flex-col items-center h-fit relative w-full bg-space bg-cover bg-center px-8">
      <div
        id="title"
        className="flex flex-wrap w-full items-center justify-center px-3"
      >
        <h1 className="flex-1 sticky mr-2 py-10 text-center md:text-start font-bold text-5xl">
          Latest Events
        </h1>
        <SearchBar onInput={handleInputChange} />
      </div>

      <FilterBar
        onCheck={handleCheckboxChange}
        onDateChange={handleDateChange}
      />

      <Grid className="w-full" container spacing={3}>
        {displayedItems}
      </Grid>
    </main>
  );
}
