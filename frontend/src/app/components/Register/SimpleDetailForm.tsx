import { useState } from "react";

import { Grid, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { useFormState } from "./FormContext";

// take note of the values recorded in this form
type TFormValues = {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
};

export function SimpleDetailForm() {
  const { onHandleNext, setFormData, formData } = useFormState();
  const { register, handleSubmit } = useForm<TFormValues>({
    defaultValues: formData,
  });

  const [msg, setMsg] = useState("");

  const onHandleFormSubmit = (data: TFormValues) => {
    if (!isValid(data.email, data.password, data.confirmPassword)) {
      if (data.password.length < 8) {
        setMsg("Password should be at least 8 characters");
      } else if (
        !data.email.match(
          /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
        )
      ) {
        setMsg("Email is invalid");
      } else if (data.password !== data.confirmPassword) {
        setMsg("Passwords are not consistent");
      } else {
        setMsg("loading...");
      }
      return;
    }

    setFormData((prev: any) => ({ ...prev, ...data }));
    onHandleNext();
  };

  // async function checkUsernameValidity(username: string) {
  //   const isValid = await usernameCheck(username);
  //   return isValid;
  // }

  const isValid = (
    email: string,
    password: string,
    confirmPassword: string
  ) => {
    // const usernameValidity = await checkUsernameValidity(username);
    // if (usernameValidity) {
    //   console.log(usernameValidity);
    //   return false;
    // }
    if (
      !email.match(
        /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
      )
    )
      return false;
    if (password !== confirmPassword) return false;
    if (password.length < 8) return false;
    return true;
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

  return (
    <form
      className="mt-5 w-full max-w-sm"
      onSubmit={handleSubmit(onHandleFormSubmit)}
    >
      <Grid container spacing={1.5}>
        <Grid item xs={12} sm={6}>
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
        <Grid item xs={12} sm={6}>
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
        <Grid item xs={12}>
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
        <Grid item xs={12} marginBottom={1.5}>
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
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="password"
            label="Password"
            type="password"
            fullWidth
            autoComplete="password"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
            {...register("password")}
          />
        </Grid>
        <Grid item xs={12}>
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
      </Grid>

      <button className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue">
        Continue
      </button>
      <div className="text-red-500 mt-2">{msg}</div>
    </form>
  );
}
