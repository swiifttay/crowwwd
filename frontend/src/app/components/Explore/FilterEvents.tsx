import { Grid } from "@mui/material";
import EventCard from "./EventCard";

export default function FilterEvents() {
  return (
    <Grid container spacing={1}>
      <Grid item lg={4} md={4} sm={6}>
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
