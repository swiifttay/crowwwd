"use client";

import Button from "@mui/material/Button";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import React, { useState } from "react";
import { registerAccount } from "../../axios/apiService";

import { useRouter } from 'next/navigation';
import { useFormState } from "./FormContext";
import { useForm } from "react-hook-form";

type TFormValues = {
  phoneNo: string;
  address: string;
  city: string;
  state: string;
  postalCode: string;
  countryOfResidence: string;
  saveAddress: boolean;

  // dateOfBirth: string;
};

export function ComplexDetailForm() {
  const router = useRouter()

  const { onHandleBack, setFormData, formData } = useFormState();
  const { register, handleSubmit } = useForm<TFormValues>({
    defaultValues: formData,
  });

  const [msg, setMsg] = useState("");

  const onHandleFormSubmit = async (data: TFormValues) => {
    console.log({data});
    // check for validity
    if (!/^\d+$/.test(data.phoneNo)) {
      setMsg("Mobile number should be digits");
      return;
    }

    if (data.phoneNo.length < 8) {
      setMsg("Mobile number should be at least 8 characters");
      return;
    }

    if (!/^\d+$/.test(data.postalCode)) {
      setMsg("Postal code should be digits");
      return;
    }

    setMsg("yay");
    await setFormData((prev: any) => ({ ...prev, ...data }));
    console.log({data});
    await handleRegister();
  };

  async function handleRegister() {
    registerAccount(formData);
    // router.push("/login");

  }

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
    <>
      <Typography
        variant="h6"
        className="font-mont text-white mb-5"
        gutterBottom
      > {
          "Hi " + formData.username + ","
        }
        <br></br>
        just a few more information!
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="phoneNo"
            label="Mobile number"
            fullWidth
            autoComplete="mobile-number"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("phoneNo")}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="address"
            label="Address line"
            fullWidth
            autoComplete="shipping address-line"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("address")}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="city" 
            label="City"
            fullWidth
            autoComplete="shipping address-level2"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("city")}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            id="state" 
            label="State/Province/Region"
            fullWidth
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("state")}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="postalCode"
            label="Zip / Postal code"
            fullWidth
            autoComplete="shipping postal-code"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("postalCode")}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="countryOfResidence"
            label="Country"
            fullWidth
            autoComplete="shipping country"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("countryOfResidence")}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControlLabel
            control={
              <Checkbox
                color="primary"
                value="yes"
                className="text-white"
                {...register("saveAddress")}
              />
            }
            label="Use this address for payment details"
            className="text-white font-mont"
          />
        </Grid>
      </Grid>
      <form onSubmit={handleSubmit(onHandleFormSubmit)}>
        <div className="text-red-500 mt-2">{msg}</div>

        <div>
          <button className="mt-4 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
            onClick={onHandleBack}>
            Back
          </button>
          <button className="mt-4 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue">
            Submit
          </button>
        </div>
      </form>
    </>
  );
}
