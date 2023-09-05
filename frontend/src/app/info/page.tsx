"use client";

import RootLayout from "@/app/layout";
import React, { useState } from "react";
import { COUNTRIES } from "../components/CountryPicker/countries";
import CountrySelector from "../components/CountryPicker/selector";
import intlTelInput from "../components/TeleInput/src/js/intlTelInput";

function Info() {
  const myRef = React.createRef<HTMLDivElement>();

  const [isOpen, setIsOpen] = useState(false);
  // Default this to a country's code to preselect it
  const [country, setCountry] = useState("AF");

  const input = document.querySelector('#phone');
  intlTelInput(input, {utilsScript: "../components/TeleInput/src/js/utils.js"})

  return (
      <div className="flex flex-col items-center justify-center min-h-screen py-2">
        <main className="flex relative flex-col justify-center w-full flex-1 px-20 text-center">
          <form className="mt-8 w-full max-w-sm">
            <div className="">
              <input
                type="text"
                id="title"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-white mb-4"
                placeholder="Title"
              />
              <input
                type="text"
                id="firstName"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-white mb-4"
                placeholder="First Name"
              />
              <input
                type="text"
                id="lastName"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-white mb-4"
                placeholder="Last Name"
              />
              <input
                type="text"
                id="nationality"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-white mb-4"
                placeholder="Nationality"
              />
              <input
                type="text"
                id="Country"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-white mb-4"
                placeholder="Country of Residence"
              />
            </div>
            <CountrySelector
              id={"countries"}
              open={isOpen}
              onToggle={() => setIsOpen(!isOpen)}
              onChange={(val) => setCountry(val)}
              // We use this type assertion because we are always sure this find will return a value but need to let TS know since it could technically return null
              selectedValue={
                COUNTRIES.find(
                  (option) => option.value === country
                ) as SelectMenuOption
              }
            />

            <button
              type="submit"
              className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
            >
              Save
            </button>
          </form>
        </main>
      </div>
  );
}

export default Info;
