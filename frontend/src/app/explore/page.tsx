"use client";

import FilterBar from "../components/Explore/FilterBar";
import FilterEvents from "../components/Explore/FilterEvents";
import "../globals.css";
import { Grid } from "@mui/material";

import { useState, useEffect } from "react";
import { concertsList as eventsList } from "../axios/apiService";
import FilterSearch from "../components/Explore/FilterSearch";

export interface Event {
  name: string;
  eventImageName: string;
  description: string;
  venue: string;
  categories: [string];
  artistId: string;
  seatingImagePlan: string;
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
  const handleCalendarChange = (e) => {
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

    return filteredEvents.map(({name, eventImageName}) => {})
  };

  return (
    <main className="relative h-max guide">
      <h1 className="sticky font-bold text-5xl guide px-20 py-10">
        Upcoming Events
      </h1>
      <div id="event-items" className="w-full">
        <Grid container>
          <Grid item xs={12}><FilterSearch onChange={handleInputChange} /></Grid>
          <Grid item sm={3} md={2}><FilterBar onChange={handleCheckboxChange} /></Grid>
          <Grid item sm={9} md={10} className="justify-center"><FilterEvents concertsList={events} /></Grid>
        </Grid>
      </div>
    </main>
  );
}
