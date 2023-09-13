import { TextField } from "@mui/material";
import DataEntry from "../Login/DataEntry";
import { IoSearchSharp } from "react-icons/io5";

export default function FilterSearch() {
  return (
    <div className="flex">
      <TextField
        className="rounded-lg"
        variant="filled"
        color="success"
        id="outlined-search"
        label={<IoSearchSharp />}
        type="search"
      />
    </div>
  );
}
