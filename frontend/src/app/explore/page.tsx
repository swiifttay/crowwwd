"use client";

import FilterBar from "../components/Explore/FilterBar";
import FilterEvents from "../components/Explore/FilterEvents";
import "../globals.css";
import { Grid } from "@mui/material";

import { useState, useEffect } from "react";
import { concertsList } from "../axios/apiService";
import FilterSearch from "../components/Explore/FilterSearch";

export interface Concert {
  name: string;
  eventImageName: string;
  description: string;
  venu: string;
  categories: [string];
  artistId: string;
  seatingImagePlan: string;
}

export default function Explore() {
  const [concerts, setConcerts] = useState<Concert[]>([]);

  useEffect(() => {
    fetchConcerts();
  }, []);

  const fetchConcerts = async () => {
    const response: Concert[] = await concertsList();
    setConcerts(response);
  };

  return (
    <main className="flex h-max">
      <div id="event-items" className="mt-40 w-full">
        <Grid container>
          <Grid item xs={12}>
            <FilterSearch />
          </Grid>
          <Grid item sm={3} md={2}>
            <FilterBar />
          </Grid>
          <Grid item sm={9} md={10} className="justify-center">
            <FilterEvents concertsList={concerts} />
          </Grid>
        </Grid>
      </div>
    </main>
  );
}
