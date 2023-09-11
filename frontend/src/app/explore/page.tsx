import ActionAreaCard from "../components/Explore/EventCard";
import FilterBar from "../components/Explore/FilterBar";
import FilterEvents from "../components/Explore/FilterEvents";
import "../globals.css";
import { Grid } from "@mui/material";

import {useState, useEffect} from "react";
import { eventsList } from "../axios/apiService";

export interface Event {
  name: string;
  eventImageName: string;
  description: string;
  venu: string
  categories: [string];
  artistId: string;
  seatingImagePlan: string;
}

export default function Explore() {

  // const [events, setEvents] = useState([]);

  // useEffect(() => {
  //   fetchEvents();
  // })

  // const fetchEvents = async () => {
  //   const response = await eventsList();
  //   setEvents(response);
  // }

  return (
    <main className="flex h-max">
      <div id="event-items" className="flex flex-row mt-40 w-full">
        <Grid container>
          <Grid item sm={3} md={2}>
            <FilterBar />
          </Grid>
          <Grid item sm={9} md={10} className="justify-center">
            <FilterEvents />
            {/* <FilterEvents eventList={eventList} /> */}
          </Grid>
        </Grid>
      </div>
    </main>
  );
}
