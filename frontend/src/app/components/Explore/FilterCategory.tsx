"use client";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import {
  createTheme,
  FormGroup,
  ThemeProvider,
  Typography,
} from "@mui/material";

const theme = createTheme({
  palette: {
    primary: {
      main: "#07172F",
    },
  },
});

export default function FilterCategory() {
  return (
    <FormControl className="w-full" variant="filled">
      <FormLabel
        color="success"
        id="filter-category"
        className="font-mont text-sm text-center text-white"
      >
        Categories
      </FormLabel>
      <FormGroup defaultValue="pop">
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
          control={<Checkbox size="small" />}
          label={<Typography className="font-mont text-xs">Indie</Typography>}
        />
      </FormGroup>
    </FormControl>
  );
}
