"use client";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import { useState } from "react";
import { registerAccount } from "../axios/apiService";
import "./style.css";

import { Button } from "@mui/material";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { useFormState } from "../components/Register/FormContext";

type TFormValues = {
  phoneNo: string;
  address: string;
  city: string;
  state: string;
  postalCode: string;
  countryOfResidence: string;
  saveAddress: boolean;
};

export default function ComplexDetailForm() {
  const router = useRouter();

  const { onHandleBack, setFormData, formData } = useFormState();
  const { register, handleSubmit } = useForm<TFormValues>({
    defaultValues: formData,
  });

  const [msg, setMsg] = useState("");

  const onHandleFormSubmit = async (data: TFormValues) => {
    console.log({ data });
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

    setMsg("loading...");
    setFormData((prev: any) => ({ ...prev, ...data }));
    await handleRegister(data);
  };

  async function handleRegister(data: any) {
    const response = await registerAccount(data);

    // check if the status given is correct
    if (response.request?.status === 200) {
      setMsg("Saving...");
      router.push("/login");
    } else {
      setMsg("Try Again Later!");
    }
  }

  const inputStyles = {
    color: "white",
  };

  const sxStyles = {
    "& .MuiOutlinedInput-input": {
      color: "black",
      zIndex: 10,
    },
    "& .MuiOutlinedInput-root": {
      "& fieldset": {
        borderColor: "theme-light-grey",
        backgroundColor: "rgba(241, 245, 249, 0.5)",
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
    <div className="flex items-top justify-center w-full overflow-hidden bg-login bg-center bg-cover pb-20">
      <div className="flex flex-col items-center justify-center w-full text-center lg:w-1/2 sm:w-[80vw] xs:w-[40vw]">
        <Typography
          variant="h6"
          className="font-mont text-lg text-white mt-5 mb-5"
          gutterBottom
        >
          {"Update your information here."}
        </Typography>
        <Grid container spacing={1.5} alignItems="center" justifyContent="center">
          <Grid item xs={10} sm={6}>
            <TextField
              sx={sxStyles}
              required
              id="firstName"
              label="First Name"
              fullWidth
              autoComplete="firstName"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("firstName")}
            />
          </Grid>
          <Grid item xs={10} sm={6}>
            <TextField
              sx={sxStyles}
              required
              id="lastName"
              label="Last Name"
              fullWidth
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("lastName")}
            />
          </Grid>
          <Grid item xs={10} sm={4}>
            <TextField
              sx={sxStyles}
              required
              id="username"
              label="Username"
              fullWidth
              autoComplete="username"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("username")}
            />
          </Grid>
          <Grid item xs={10} sm={4}>
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
          <Grid item xs={10} sm={4}>
            <TextField
              sx={sxStyles}
              required
              id="email"
              label="Email"
              type="email"
              fullWidth
              autoComplete="email"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("email")}
            />
          </Grid>
          <Grid item xs={10} sm={12}>
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
          <Grid item xs={10} sm={3}>
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
          <Grid item xs={10} sm={3}>
            <TextField
              sx={sxStyles}
              required
              id="state"
              label="State"
              fullWidth
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("state")}
            />
          </Grid>
          <Grid item xs={10} sm={3}>
            <TextField
              sx={sxStyles}
              required
              id="postalCode"
              label="Postal code"
              fullWidth
              autoComplete="shipping postal-code"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("postalCode")}
            />
          </Grid>
          <Grid item xs={10} sm={3}>
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

          <Grid item xs={10} sm={12} marginTop={2.5}>
            <TextField
              sx={sxStyles}
              required
              id="oldPassword"
              label="Old Password"
              type="oldPassword"
              fullWidth
              autoComplete="oldPassword"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("oldPassword")}
            />
          </Grid>
          <Grid item xs={10} sm={12}>
            <TextField
              sx={sxStyles}
              required
              id="newPassword"
              label="New Password"
              type="password"
              fullWidth
              autoComplete="newPassword"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("newPassword")}
            />
          </Grid>
          <Grid item xs={10} sm={12}>
            <TextField
              sx={sxStyles}
              required
              id="confirmPassword"
              label="Confirm Password"
              type="password"
              fullWidth
              autoComplete="confirmPassword"
              variant="outlined"
              InputProps={{ style: inputStyles }}
              InputLabelProps={{ style: inputStyles }}
              {...register("confirmPassword")}
            />
          </Grid>

          <Grid item xs={8} sm={10} marginBottom={1.5}>
            <FormControlLabel
              control={
                <Checkbox
                  color="primary"
                  value="no"
                  className="text-white"
                  {...register("saveAddress")}
                />
              }
              label="I would like to be provided with marketing information"
              className="text-white font-mont"
            />
          </Grid>
        </Grid>
        <form className="" onSubmit={handleSubmit(onHandleFormSubmit)}>
          <div className="text-red-500 mt-1 mb-4">{msg}</div>

          <div className="flex justify-between px-2">
            <Button
              onClick={onHandleBack}
              className="text-theme-blue hover:text-theme-light-blue rounded-lg -mt-1"
            >
              Back
            </Button>
            <button className="w-fit-content bg-theme-blue text-white py-2 px-8 rounded-lg hover:bg-theme-light-blue">
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
