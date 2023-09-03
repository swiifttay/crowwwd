"use client";

import React, { useState } from "react";
import DataEntry from "../components/Login/DataEntry";
import {login} from "../axios/apiService";

export default function LoginForm() {
  const [enteredUsername, setEnteredUsername] = useState("");
  const [enteredPassword, setEnteredPassword] = useState("");

  const submitHandler = async (e: any) => {
    e.preventDefault();

    const formData = {
      username: enteredUsername,
      password: enteredPassword,
    };

    console.log(formData);


    try {
      const response = await login(enteredUsername, enteredPassword)
      
    } catch (error){
      console.log(error);
    }

  };

  const updateTextHandler = (enteredText: string, id: string) => {
    if (id == "username") {
      setEnteredUsername(enteredText);
    } else {
      setEnteredPassword(enteredText);
    }
  };

  return (
    <form className="mt-8 w-full max-w-sm" onSubmit={submitHandler}>
      <DataEntry
        type="text"
        id="username"
        placeholder="Enter your username/email"
        onTextChange={updateTextHandler}
      />
      <DataEntry
        type="password"
        id="password"
        placeholder="Enter your password"
        onTextChange={updateTextHandler}
      />

      <button
        type="submit"
        className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      >
        Log In
      </button>
    </form>
  );
}
