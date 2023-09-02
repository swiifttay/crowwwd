"use client"

import React, { useState } from "react";
import DataEntry from "../components/Login/DataEntry";

export default function RegisterForm() {
  const [enteredFirstName, setEnteredFirstName] = useState("");
  const [enteredLastName, setEnteredLastName] = useState("");
  const [enteredUsername, setEnteredUsername] = useState("");
  const [enteredPassword, setEnteredPassword] = useState("");

  const submitHandler = (e: any) => {
    e.preventDefault();

    const formData = {
      firstName: enteredFirstName,
      lastName: enteredLastName,
      username: enteredUsername,
      password: enteredPassword,
    };

    console.log(formData);
  };

  const updateTextHandler = (enteredText: string, id: string) => {
    if (id == "firstName") {
      setEnteredFirstName(enteredText);
    } else if (id == "lastName") {
      setEnteredLastName(enteredText);
    } else if (id == "username") {
      setEnteredUsername(enteredText);
    } else if (id == "password") {
      setEnteredPassword(enteredText);
    }
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
