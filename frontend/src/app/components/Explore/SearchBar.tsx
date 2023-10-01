import { ChangeEvent, useRef } from "react";
import { BiSearch } from "react-icons/bi";

type InputHandler = {
  onInput: (input: string) => void;
};

export function SearchBar({ onInput }: InputHandler) {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleSubmit = (e: ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (inputRef.current) {
      onInput(inputRef.current.value);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="h-16 py-5 px-8 border-none flex items-center radius rounded-full bg-cover bg-center bg-hollow-purple"
    >
      <input
        ref={inputRef}
        type="text"
        placeholder="Search Events"
        name="search"
        autoComplete="off"
        className="bg-transparent border-none text-lg text-white placeholder-slate-400 p focus:outline-none"
      ></input>
      <button type="submit">
        <BiSearch className="text-xl text-slate-400 hover:text-white" />
      </button>
    </form>
  );
}
