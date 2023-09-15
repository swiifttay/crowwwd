import { BiSearch } from "react-icons/bi";

export function SearchBar() {
  return (
    <form
      action=""
      className="h-24 md:h-28 w-84 md:mr-6 max-w-md py-5 px-12 sm:py-2 border-none  flex items-center radius rounded-full bg-cover bg-center bg-hollow-purple"
    >
      <input
        type="text"
        placeholder="Search Events"
        name="search"
        autoComplete="off"
        className="bg-transparent flex-1 border-none lg:py-5 px-8 text-lg text-white placeholder-slate-400 p focus:outline-none"
      ></input>
      <button type="submit">
        <BiSearch className="text-xl text-slate-400 hover:text-white" />
      </button>
    </form>
  );
}
