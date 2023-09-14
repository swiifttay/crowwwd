"use client";

import { Grid } from "@mui/material";
import EventCard from "./EventCard";
import { Concert } from "@/app/explore/page";

export default function FilterEvents(concertsList: {
  concertsList: Concert[];
}) {
  return (
    <Grid container spacing={3}>
      {/* {props?.concertsList.map(() => {
        <Grid item lg={4} md={4} sm={6} className="w-full">
          <EventCard />
        </Grid>;
      })} */}

      <Grid item lg={4} md={4} sm={6} className="w-full">
        <EventCard />
      </Grid>
      <Grid item lg={4} md={4} sm={6}>
        <EventCard />
      </Grid>
      <Grid item lg={4} md={4} sm={6}>
        <EventCard />
      </Grid>
      <Grid item lg={4} md={4} sm={6}>
        <EventCard />
      </Grid>
    </Grid>
  );
}
