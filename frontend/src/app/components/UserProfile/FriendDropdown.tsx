import * as React from 'react';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

const nameList = ["Alice", "Bob", "Charlie", "David"]; // Replace with your list of names

export default function BasicSelect() {
  const [selectedName, setSelectedName] = React.useState('');

  const handleChange = (event: SelectChangeEvent) => {
    setSelectedName(event.target.value as string);
  };

  return (
    <Box sx={{ minWidth: 120 }}>
      <FormControl fullWidth>
        <InputLabel id="demo-simple-select-label">Friend</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={selectedName}
          label="Select a name"
          onChange={handleChange}
        >
          {nameList.map((name, index) => (
            <MenuItem key={index} value={name}>
              {name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Box>
  );
}
