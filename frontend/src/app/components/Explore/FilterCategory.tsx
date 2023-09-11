"use client"

import * as React from "react";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";

export default function FilterCategory() {
  return (
    <FormControl className="w-full" variant="filled">
      <FormLabel color="success" id="filter-category" className="font-mont text-sm text-center">Category</FormLabel>
      <RadioGroup
        defaultValue="pop"
        name="category-selection"
        className="guide"
      >
        <FormControlLabel className="justify-center cd frontendtext-sm" value="pop" control={<Radio size="small" />} label="Pop" />
        <FormControlLabel className="justify-center" value="jazz" control={<Radio size="small"/>} label="Jazz" />
        <FormControlLabel className="justify-center" value="Indie" control={<Radio size="small"/>} label="Indie" />
      </RadioGroup>
    </FormControl>
  );
}
