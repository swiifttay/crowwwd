import { User } from "@/app/userprofile/page";
import React from "react";
import VerticalCard from "./VerticalCard";

/* CURRENTLY NOT IN USE */
interface SearchResultsProps {
  results: User[];
}

const SearchResults: React.FC<SearchResultsProps> = ({ results }) => {
  return (
    <div className="absolute top-12 left-0 w-full bg-white border shadow-lg">
      {results.map((user) => (
        <VerticalCard key={user.id} name={user.firstName} />
      ))}
    </div>
  );
};

export default SearchResults;
