"use client";

import React, { useState } from "react";
import DataEntry from "../components/Login/DataEntry";
import { register } from "../axios/apiService";
import axios, { AxiosError } from "axios";

export default function RegisterForm() {
  const [registerDetails, setRegisterDetails] = useState({
    firstName: "", lastName: "", username: "", password: "",
  })

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(registerDetails);
    register(registerDetails);
  };

  const updateTextHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRegisterDetails(prevState => {
      return {...prevState, [e.target.id]: e.target.value}
    })
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
        placeholder="Username/email"
        onTextChange={updateTextHandler}
      />
      <DataEntry
        type="password"
        id="password"
        placeholder="Password"
        onTextChange={updateTextHandler}
      />

      <button
        type="submit"
        className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      >
        Sign Up
      </button>
    </form>
  );
}
