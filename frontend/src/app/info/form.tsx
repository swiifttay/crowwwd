import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import React, { useState } from "react";
import { register } from "../axios/apiService";
import "./style.css";

export default function AddressForm() {
  const [addressDetails, setAddressDetails] = useState({
    firstName: "",
    lastName: "",
    phoneNo: "",
    address: "",
    nationality: "", //city
    countryCode: "", //state
    postalCode: "",
    countryOfResidence: "",

    username: "A",
    password: "A",
    email: "A",
    confirmPassword: "A",
    gender: "A",
    dateOfBirth: "A",
  });

  const [msg, setMsg] = useState("");

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

      if (!/^\d+$/.test(addressDetails.phoneNo)) {
        setMsg("Mobile number should be digits");
        return;
      }

      if (addressDetails.phoneNo.length < 8) {
        setMsg("Mobile number should be at least 8 characters");
        return;
      }

      if (!/^\d+$/.test(addressDetails.postalCode)) {
        setMsg("Postal code should be digits");
        return;
      }

    register(addressDetails);
  };

  const updateTextHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAddressDetails((prevState) => {
      return { ...prevState, [e.target.id]: e.target.value };
    });
  };

  const inputStyles = {
    color: "white",
  };

  const sxStyles = {
    "& .MuiOutlinedInput-root": {
      "& fieldset": {
        borderColor: "theme-light-grey",
      },
      "&:hover fieldset": {
        borderColor: "white",
      },
      "&.Mui-focused fieldset": {
        borderColor: "white",
      },
    },
    "& label.Mui-focused": {
      color: "white",
    },
  };

  return (
    <form onSubmit={submitHandler}>
      <Typography
        variant="h6"
        className="font-mont text-white mb-5"
        gutterBottom
      >
        Personal Information
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="firstName"
            label="First Name"
            fullWidth
            autoComplete="given-name"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="lastName"
            name="lastName"
            label="Last Name"
            fullWidth
            autoComplete="family-name"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="phoneNo"
            name="phoneNo"
            label="Mobile number"
            fullWidth
            autoComplete="mobile-number"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="address"
            name="address"
            label="Address line"
            fullWidth
            autoComplete="shipping address-line"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="nationality" //in place of city, for now
            name="city"
            label="City"
            fullWidth
            autoComplete="shipping address-level2"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            id="countryCode" //in place of state, for now
            name="state"
            label="State/Province/Region"
            fullWidth
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="postalCode"
            name="postalCode"
            label="Zip / Postal code"
            fullWidth
            autoComplete="shipping postal-code"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="countryOfResidence"
            name="countryOfResidence"
            label="Country"
            fullWidth
            autoComplete="shipping country"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            onChange={updateTextHandler}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControlLabel
            control={
              <Checkbox
                color="primary"
                name="saveAddress"
                value="yes"
                className="text-white"
              />
            }
            label="Use this address for payment details"
            className="text-white font-mont"
          />
        </Grid>
        <div className="text-red-500 mt-2">{msg}</div>
      </Grid>
    </form>
  );
}
