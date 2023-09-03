"use client";

import React, { useState } from "react";

export default function DataEntry(props: any) {
  const [enteredText, setEnteredText] = useState("");

  const textChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    props.onTextChange(e.currentTarget.value, props.id);
  };

  return (
    <div className="flex-1 mb-4">
      <input
        className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
        onChange={textChangeHandler}
        type={props.type}
        id={props.id}
        placeholder={props.placeholder}
      />
    </div>
  );
}
