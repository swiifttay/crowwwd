"use client";

import React, { useState } from "react";
import DataEntry from "../components/Login/DataEntry";
import { authenticate } from "../axios/apiService";
import axios from "axios";

export default function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await authenticate(username, password);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log(error.status);
        console.error(error.response);
      }
    }
  };

  const updateTextHandler = (enteredText: string, id: string) => {
    if (id == "username") {
      setUsername(enteredText);
    } else {
      setPassword(enteredText);
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
