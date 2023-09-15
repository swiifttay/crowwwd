"use client";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import {FormGroup} from "@mui/material";

export default function FilterCategory() {
  return (
      <FormGroup className="absolute">
        {["Pop", "Jazz", "Indie", "K Pop", "Singapore"].map((genre) => (
          <FormControlLabel
            className="justify-start text-sm"
            value={genre.toLocaleLowerCase}
            control={<Checkbox size="small" sx={{ color: "white" }} />}
            label={<span className="text-sm font-mont">{genre}</span>}
          />
        ))}
      </FormGroup>
  );
}
