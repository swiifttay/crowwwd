import { BsFilter } from "react-icons/bs";

export default function FilterBar() {
  return (
    <div className="h-full sticky mr-2 border-e-2 border-gray-500">
      <h1 className="text-sm py-3 justify-center flex flex-row">
        Filters<BsFilter />
      </h1>
    </div>
  );
}
