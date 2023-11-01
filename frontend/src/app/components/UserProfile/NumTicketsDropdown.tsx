import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import * as React from "react";

const numList = ["1", "2", "3", "4"];
export default function TicketSelect() {
  const [numberOfTickets, setNumberOfTickets] = React.useState(1);

  const handleChange = (event: SelectChangeEvent) => {
    setNumberOfTickets(event.target.value as string);
  };

  return (
    <Box sx={{ minWidth: 120 }}>
      <FormControl fullWidth>
        <InputLabel id="demo-simple-select-label">Number of Tickets</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={numberOfTickets}
          label="Number of Tickets"
          onChange={handleChange}
        >
          {/* {[1, 2, 3, 4].map((count) => (
            <MenuItem key={count} value={count}>
              {count}
            </MenuItem>
          ))} */}
          {numList.map((name, index) => (
            <MenuItem key={index} value={name}>
              {name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Box>
  );
}
