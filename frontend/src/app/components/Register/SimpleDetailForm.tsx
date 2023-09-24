import React, { useState } from "react";
import DataEntry from "../Login/DataEntry";
import Link from "next/link";

import { useFormState } from "./FormContext";
import { useForm } from "react-hook-form";

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

  const [registerDetails, setRegisterDetails] = useState({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    confirmPassword: "",
    email: ""
  });

  const { onHandleNext, setFormData, formData } = useFormState();
  const { register, handleSubmit } = useForm<TFormValues>({
    defaultValues: formData,
  });

  const onHandleFormSubmit = (data: TFormValues) => {

    isValid(registerDetails.email, registerDetails.password, registerDetails.confirmPassword);
    setFormData((prev: any) => ({ ...prev, ...data }));
    console.log({data});
    onHandleNext();
  };

  const [msg, setMsg] = useState("");


  const updateTextHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRegisterDetails((prevState) => {
      return { ...prevState, [e.target.id]: e.target.value };
    });
  };

  const isValid = (email: string,
    password: string,
    confirmPassword: string) => {

    if (password.length < 8) {
      setMsg("Password should be at least 8 characters");
    } else if (
      !email.match(
        /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
      )
    ) {
      setMsg("Email is invalid");
    } else if (password !== confirmPassword) {
      setMsg("Passwords are not consistent");
    }

    setMsg("loading...");
  };

  return (
    <form className="mt-8 w-full max-w-sm" onSubmit={handleSubmit(onHandleFormSubmit)}>
      <div className="flex space-x-2">
        <DataEntry
          type="text"
          id="firstName"
          placeholder="First Name"
          onTextChange={updateTextHandler}
        // value={registerDetails.firstName}
        />
        <DataEntry
          type="text"
          id="lastName"
          placeholder="Last Name"
          onTextChange={updateTextHandler}
        // value={registerDetails.lastName}
        />
      </div>
      <DataEntry
        type="text"
        id="username"
        placeholder="Username"
        onTextChange={updateTextHandler}
      // value={registerDetails.username}
      />
      <DataEntry
        type="text"
        id="email"
        placeholder="Email"
        onTextChange={updateTextHandler}
      // value={registerDetails.email}
      />
      <DataEntry
        type="password"
        id="password"
        placeholder="Password"
        onTextChange={updateTextHandler}
      // value={registerDetails.password}
      />
      <DataEntry
        type="password"
        id="confirmPassword"
        placeholder="Confirm Password"
        onTextChange={updateTextHandler}
      // value={registerDetails.confirmPassword}
      />

      {/* <Link href="/info"> */}
      <button className="mt-4 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      onSubmit={handleSubmit(onHandleFormSubmit)}>
        Sign Up
      </button>
      {/* </Link> */}
      <div className="text-red-500 mt-2">{msg}</div>
    </form>
  );
}
