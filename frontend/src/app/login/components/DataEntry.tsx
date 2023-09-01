import React from "react";

export default function DataEntry(props: any) {
  return (
    <div className="mb-4">
      <input
        className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
        type={props.text}
        id={props.username}
        placeholder={props.placeholder}
      />
    </div>
  );
}
