"use client";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";
import { getUserProfile, updateUserProfile } from "../axios/apiService";
import "./style.css";

import { Button } from "@mui/material";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";

type TFormValues = {
  firstName: string;
  lastName: string;
  username: string | null;
  email: string;

  oldPassword: string | null;
  newPassword: string | null;
  repeatNewPassword: string | null;

  phoneNo: string;
  address: string;
  city: string;
  state: string;
  postalCode: string;
  countryOfResidence: string;
  isPreferredMarketing: boolean;
};

const formData: TFormValues = {
  // Initial values for the form fields
  firstName: " ",
  lastName: " ",
  username: " ",
  email: " ",
  oldPassword: "",

  newPassword: "",
  repeatNewPassword: "",

  phoneNo: " ",
  address: " ",
  city: " ",
  state: " ",
  postalCode: " ",
  countryOfResidence: " ",
  isPreferredMarketing: false,
};

export default function UpdateProfile() {
  const router = useRouter();

  const { register, handleSubmit, setValue } = useForm<TFormValues>({
    defaultValues: formData,
  });

  const [msg, setMsg] = useState("");

  useEffect(() => {
    fetchUsersOriginalData();
  }, [formData]);

  const fetchUsersOriginalData = async () => {
    try {
      const response = await getUserProfile();
      if (response.data && response.data.user) {
        const userData = response.data.user as TFormValues;
        const validKeys = Object.keys(userData).filter((key) =>
          Object.keys(formData).includes(key),
        );
        validKeys.forEach((key) => {
          setValue(
            key as keyof TFormValues,
            userData[key as keyof TFormValues],
          );
          (formData as any)[key] = userData[key as keyof TFormValues];
        });
      }
    } catch (error) {
      console.error("Error fetching user profile:", error);
    }
  };

  const isValid = (
    email: string,
    newPassword: string,
    confirmPassword: string,
  ) => {
    if (
      !email.match(
        /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/,
      )
    )
      return false;
    if (newPassword !== confirmPassword) return false;
    if (newPassword.length < 8) return false;
    return true;
  };

  const onHandleBack = async () => {
    router.push("/userprofile");
  };
  const onHandleFormSubmit = async (data: TFormValues) => {
    // check if username is null
    if (data.username === formData.username) {
      data.username = null;
    }
    // check if password was changed
    if (data.oldPassword === "") {
      data.oldPassword = null;
      data.newPassword = null;
      data.repeatNewPassword = null;
    }

    if (
      !data.email.match(
        /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/,
      )
    ) {
      setMsg("Email is invalid");
      return;
    }

    try {
      const response = await updateUserProfile(data);

      if (response.request?.status === 200) {
        setMsg("Saving...");
        router.push("/userprofile");
      } else {
        setMsg(response.data?.message);
      }
    } catch (error) {
      console.log(error);
    }
  };

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

  const [isDropdownOpen, setDropdownOpen] = useState(false);

  const toggleFields = () => {
    setDropdownOpen(!isDropdownOpen);
  };

  /* TO DO:
  1. call for user old data, put inside the textfields
  2. check if the original text fields and new textfields are any different
  3. if username is the same, pass into updateProfile as null

  >> create a "dropdown" for change of password
  1. password textfields initially are empty
  2. if user enters anything, take it as user wants to change password
     else, pass in null for password
  */

  return (
    <main className="flex items-top justify-center w-full overflow-hidden bg-login bg-center bg-cover pb-20">
      <div className="flex flex-col items-center justify-center w-full text-center lg:w-1/2 sm:w-[80vw] xs:w-[40vw]">
        <Typography
          variant="h6"
          className="font-mont text-lg text-white mt-5 mb-6"
          gutterBottom
        >
          {"Update your information here."}
        </Typography>

        <Grid
          container
          spacing={1.5}
          alignItems="center"
          justifyContent="center"
        >
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

          <Button
            onClick={toggleFields}
            variant="contained"
            sx={{
              marginTop: 2.5,
              "&:hover": {
                backgroundColor: "bg-theme-light-blue",
              },
              "&.MuiButton-root": {
                backgroundColor: "bg-theme-blue",
              },
            }}
          >
            Change your password{" "}
            {isDropdownOpen ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />}
          </Button>
          {isDropdownOpen && (
            <>
              <Grid item xs={10} sm={12}>
                <TextField
                  sx={sxStyles}
                  required
                  id="oldPassword"
                  label="Old Password"
                  type="password"
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
                  {...register("repeatNewPassword")}
                />
              </Grid>
            </>
          )}

          <Grid item xs={8} sm={10} marginBottom={1.5}>
            <FormControlLabel
              control={
                <Checkbox
                  color="primary"
                  value="no"
                  className="text-white"
                  sx={{ color: "white" }}
                  {...register("isPreferredMarketing")}
                />
              }
              label="I would like to be provided with marketing information"
              className="text-white font-mont"
            />
          </Grid>
          <form
            className="w-full px-4"
            onSubmit={handleSubmit(onHandleFormSubmit)}
          >
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
        </Grid>
      </div>
    </main>
  );
}
