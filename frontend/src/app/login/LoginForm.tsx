"use client";

import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { authenticate } from "../axios/apiService";
import { useFormState } from "../components/Login/FormContext";

type TFormValues = {
  username: string;
  password: string;
};
export default function LoginForm() {
  const router = useRouter();

  const [msg, setMsg] = useState("");
  const onHandleFormSubmit = async (data: TFormValues) => {
    setFormData((prev: any) => ({ ...prev, ...data }));

    // gather data on response
    const response = await authenticate(data);
    // check if the status given is correct
    if (response.request?.status === 200) {
      setMsg("Loading...");
      router.push("/userprofile");

      // if it is incorrect,
    } else if (response?.status === 401) {
      setMsg("The username or password entered is incorrect.");
    }
  };

  const { setFormData, formData } = useFormState();
  const { register, handleSubmit } = useForm<TFormValues>({
    defaultValues: formData,
  });

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
      className="mt-8 w-full max-w-sm"
      onSubmit={handleSubmit(onHandleFormSubmit)}
    >
      <Grid container spacing={3}>
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
      </Grid>

      <div className="text-red-500 mt-2">{msg}</div>

      <button
        type="submit"
        className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      >
        Log In
      </button>
    </form>
  );
}
