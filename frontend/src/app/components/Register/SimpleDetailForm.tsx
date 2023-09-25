import { useState } from "react";

import { useForm } from "react-hook-form";
import { useFormState } from "./FormContext";

import { usernameCheck } from "../../axios/apiService";

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

  const isValid = (email: string,
    password: string,
    confirmPassword: string) => {
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

  return (
    <form
      className="mt-8 w-full max-w-sm"
      onSubmit={handleSubmit(onHandleFormSubmit)}
    >
      <div className="flex space-x-2">
        <input
          className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
          type="text"
          id="firstName"
          placeholder="First Name"
          {...register("firstName")}
        />
        <input
          className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
          type="text"
          id="lastName"
          placeholder="Last Name"
          {...register("lastName")}
        />
      </div>
      <input
        className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
        type="text"
        id="username"
        placeholder="Username"
        {...register("username")}
      />
      <input
        className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
        type="text"
        id="email"
        placeholder="Email"
        {...register("email")}
      />
      <input
        className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
        type="password"
        id="password"
        placeholder="Password"
        {...register("password")}
      />
      <input
        className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
        type="password"
        id="confirmPassword"
        placeholder="Confirm Password"
        {...register("confirmPassword")}
      />

      <button className="mt-4 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue">
        Continue
      </button>
      <div className="text-red-500 mt-2">{msg}</div>
    </form>
  );
}
