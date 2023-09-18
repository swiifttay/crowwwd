"use client";

import React, { useState } from "react";
import { register } from "../axios/apiService";
import DataEntry from "../components/Login/DataEntry";

export default function RegisterForm() {
  const [registerDetails, setRegisterDetails] = useState({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    email: "",
    confirmPassword: "",
    nationality: "a",
    countryOfResidence: "a",
    countryCode: "a",
    gender: "a",
    dateOfBirth: "a",
    address: "a",
    postalCode: "a",
    phoneNo: "a",
  });

  const [msg, setMsg] = useState("");

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // console.log(registerDetails);

    if (
      !isValid(
        registerDetails.email,
        registerDetails.password,
        registerDetails.confirmPassword,
      )
    ) {
      if (registerDetails.password.length < 8) {
        setMsg("Password should be at least 8 characters");
      } else if (
        !registerDetails.email.match(
          /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/,
        )
      ) {
        setMsg("Email is invalid");
      } else if (registerDetails.password !== registerDetails.confirmPassword) {
        setMsg("Passwords are not consistent");
      }
      return;
    }

    register(registerDetails);
  };

  const updateTextHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRegisterDetails((prevState) => {
      return { ...prevState, [e.target.id]: e.target.value };
    });
  };

  const isValid = (email, password, confirmPassword) => {
    if (
      !email.match(
        /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/,
      )
    )
      return false;
    if (password !== confirmPassword) return false;
    if (password.length < 8) return false;
    return true;
  };

  return (
    <form className="mt-8 w-full max-w-sm" onSubmit={submitHandler}>
      <div className="flex space-x-2">
        <DataEntry
          type="text"
          id="firstName"
          placeholder="First Name"
          onTextChange={updateTextHandler}
        />
        <DataEntry
          type="text"
          id="lastName"
          placeholder="Last Name"
          onTextChange={updateTextHandler}
        />
      </div>
      <DataEntry
        type="text"
        id="username"
        placeholder="Username"
        onTextChange={updateTextHandler}
      />
      <DataEntry
        type="text"
        id="email"
        placeholder="Email"
        onTextChange={updateTextHandler}
      />
      <DataEntry
        type="password"
        id="password"
        placeholder="Password"
        onTextChange={updateTextHandler}
      />
      <DataEntry
        type="password"
        id="confirmPassword"
        placeholder="Confirm Password"
        onTextChange={updateTextHandler}
      />

      <button
        type="submit"
        className="mt-4 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      >
        Sign Up
      </button>
      <div className="text-red-500 mt-2">{msg}</div>
    </form>
  );
}
