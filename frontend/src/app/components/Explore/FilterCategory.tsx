"use client";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import {FormGroup} from "@mui/material";
import { ChangeEvent, SyntheticEvent } from "react";

type CheckProps = {
  onCheck: (input: ChangeEvent<HTMLInputElement>) => void
}

export default function FilterCategory({onCheck} : CheckProps) {

  const handleFormControlLabelChange = (
    event: React.SyntheticEvent<Element, Event>,
    checked: boolean
  ) => {
    onCheck(event as React.ChangeEvent<HTMLInputElement>);
  }

  return (
      <FormGroup className="w-full absolute bg-slate-700 bg-opacity-95 py-3 px-4 rounded-b-2xl">
        {["Pop", "Country-Pop", "Electronic-Based", "Rap", "Hip-Hop"].map((genre) => (
          <FormControlLabel
            onChange={handleFormControlLabelChange}
            key={genre}
            className="justify-start text-sm"
            value={genre.toLocaleLowerCase()}
            control={<Checkbox size="small" sx={{ color: "white" }} />}
            label={<span className="text-sm font-mont">{genre}</span>}
          />
        ))}
      </FormGroup>
  );
}
