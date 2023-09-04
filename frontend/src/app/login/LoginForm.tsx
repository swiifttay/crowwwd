"use client";

import React, { useState } from "react";
import DataEntry from "../components/Login/DataEntry";
import { authenticate } from "../axios/apiService";

export default function LoginForm() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(credentials);
    authenticate(credentials);
  };

  const updateTextHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCredentials((prevState) => {
      return { ...prevState, [e.target.id]: e.target.value };
    });
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
