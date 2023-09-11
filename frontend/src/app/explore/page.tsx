import ActionAreaCard from "../components/Explore/EventCard";
import FilterBar from "../components/Explore/FilterBar";
import FilterEvents from "../components/Explore/FilterEvents";
import "../globals.css";
import { Grid } from "@mui/material";

export default function Explore() {
  return (
    <main className=" h-screen flex">
      <div id="event-items" className="flex flex-row mt-40 w-full">
        <Grid container>
          <Grid item sm={3} md={2}>
            <FilterBar />
          </Grid>
          <Grid item sm={9} md={10}>
            <FilterEvents />
          </Grid>
        </Grid>
      </div>
    </main>
  );
}
