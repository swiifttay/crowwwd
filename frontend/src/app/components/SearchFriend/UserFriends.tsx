import { searchProfile } from "@/app/axios/apiService";
import { User } from "@/app/userprofile/page";
// backend\src\main\java\com\cs203\g1t4\backend\models\User.java
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import { useState } from "react";
import FriendResult from "./FriendResult";
import styles from "./SearchFriend.module.css";

export default function UserFriends() {
  const [searchVisible, setSearchVisible] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");

  async function handleSearchSubmit(event: React.FormEvent) {
    event.preventDefault();
    if (searchTerm.trim() === "") return;

    try {
      console.log(searchTerm);
      const response = await searchProfile(searchTerm);
      console.log(response.data);
      setFirstName(response.data?.user.firstName);
      setLastName(response.data?.user.lastName);
      setUsername(response.data?.user.username);
    } catch (error) {
      console.error("Error searching for profiles:", error);
    }
  }

  function handleSearchClick() {
    setSearchVisible(true);
  }

  return (
    <div className="flex flex-col w-full">
      <div className="flex flex-row justify-between mb-4 mt-16 font-bold">
        <div className=" text-xl">Friends</div>

        <div
          className={`text-lg hover:text-sky-400 text-theme-light-blue cursor-pointer ${
            searchVisible ? "hidden" : ""
          }`}
          onClick={handleSearchClick}
        >
          Search Friend
        </div>
        {searchVisible && (
          <div className="">
            <form className={styles.searchForm} onClick={handleSearchSubmit}>
              <input
                type="text"
                placeholder="Search..."
                className={styles.searchInput}
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <button type="submit" className={styles.searchButton}>
                <MagnifyingGlassIcon className="h-6 stroke-white hover-text-gray-300 cursor-pointer " />
              </button>
            </form>
            {firstName && (
              <FriendResult
                username={username}
                firstName={firstName}
                lastName={lastName}
              />
            )}
          </div>
        )}
      </div>

      <div className="flex overflow-x-auto max-w-full mb-32 px-4">
        <div className="flex gap-5">
          {/* <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
          <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" /> */}
        </div>
      </div>
    </div>
  );
}
