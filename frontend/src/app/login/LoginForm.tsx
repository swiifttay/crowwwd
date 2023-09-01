import React from "react";
import DataEntry from "../components/Login/DataEntry";

export default function LoginForm() {
  return (
    <form className="mt-8 w-full max-w-sm">
      <DataEntry
        type="text"
        id="username"
        placeholder="Enter your username/email"
      />
      <DataEntry
        type="password"
        id="password"
        placeholder="Enter your password"
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
