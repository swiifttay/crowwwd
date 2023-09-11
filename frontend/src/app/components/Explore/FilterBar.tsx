import { BsFilter } from "react-icons/bs";
import FilterCategory from "./FilterCategory";

export default function FilterBar() {
  return (
    <div className="h-full fixed mr-2 border-e-2 border-gray-500 px-12">
      <h1 className="py-3 justify-center flex flex-row text-base">
        Filters
        <BsFilter />
      </h1>
      <FilterCategory />
    </div>
  );
}
