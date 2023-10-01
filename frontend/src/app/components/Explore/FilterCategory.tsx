"use client";

import { FormGroup } from "@mui/material";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import { ChangeEvent } from "react";
import "./FilterCategory.css";

type CheckProps = {
  onCheck: (input: ChangeEvent<HTMLInputElement>) => void;
};

export default function FilterCategory({ onCheck }: CheckProps) {
  const handleFormControlLabelChange = (
    event: React.SyntheticEvent<Element, Event>,
    checked: boolean,
  ) => {
    onCheck(event as React.ChangeEvent<HTMLInputElement>);
  };

  return (
    <FormGroup className="w-full absolute bg-slate-700 bg-opacity-95 pt-4 pb-2 rounded-b-2xl">
      {["Pop", "Country-Pop", "Electronic-Based", "Rap", "Hip-Hop"].map(
        (genre) => (
          <FormControlLabel
            onChange={handleFormControlLabelChange}
            key={genre}
            className="transition hover:bg-slate-500 hover:bg-opacity-95"
            value={genre.toLocaleLowerCase()}
            control={<Checkbox size="small" sx={{ color: "white" }} />}
            label={<span className="text-sm font-mont mr-2">{genre}</span>}
          />
        ),
      )}
    </FormGroup>
  );
}
