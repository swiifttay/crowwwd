"use client";

import React, { useState } from "react";
import { useRouter } from 'next/navigation';
import DataEntry from "../components/Login/DataEntry";
import { authenticate } from "../axios/apiService";
import { error } from "console";

export default function LoginForm() {
  const router = useRouter()

  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const [isValidCredentials, setIsValidCredentials] = useState(true);

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(credentials);
    // let isValid = true;
    var isValid = await authenticate(credentials);

    console.log(isValid);
    if (!isValid) {
      console.log("invalid");
      setIsValidCredentials(false);
    } else {
      console.log("valid");
      setIsValidCredentials(true);
      router.push("/explore")
    }
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
        placeholder="Enter your username"
        onTextChange={updateTextHandler}
      />
      <DataEntry
        type="password"
        id="password"
        placeholder="Enter your password"
        onTextChange={updateTextHandler}
      />
      {isValidCredentials ? null : (
        <div>The username or password entered is incorrect.</div>
      )}

      <button
        type="submit"
        className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      >
        Log In
      </button>
    </form>
  );
}
