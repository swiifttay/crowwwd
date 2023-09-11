"use client";

import { Grid } from "@mui/material";
import EventCard from "./EventCard";

export default function FilterEvents({props}) {
  return (
    <Grid container spacing={3}>
      {/* {props?.eventList.map(() => {
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
