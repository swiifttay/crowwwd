"use client";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import { createTheme, FormGroup, Typography } from "@mui/material";

const theme = createTheme({
  palette: {
    primary: {
      main: "#07172F"
    },
  },
});

export default function FilterCategory() {
  return (
    <FormControl className="absolute" variant="filled">
      <FormGroup
        defaultValue="pop"
      >
        <FormControlLabel
          className="justify-center cd frontendtext-sm"
          value="pop"
          control={<Checkbox size="small" />}
          label={<Typography className="font-mont text-xs">Pop</Typography>}
        />
        <FormControlLabel
          className="justify-center"
          value="jazz"
          control={<Checkbox size="small" />}
          label={<Typography className="font-mont text-xs">Jazz</Typography>}
        />
        <FormControlLabel
          className="justify-center"
          value="Indie"
          control={<Checkbox size="small"  />}
          label={<Typography className="font-mont text-xs">Indie</Typography>}
        />
      </FormGroup>
    </FormControl>
  );
}