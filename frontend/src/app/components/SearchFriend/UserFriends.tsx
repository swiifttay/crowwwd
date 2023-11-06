"use client";

import { searchProfile, getPendingFriends } from "@/app/axios/apiService";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import { useState, useEffect } from "react";
import PendingRequest from "./PendingRequest";
import styles from "./SearchFriend.module.css";
import VerticalCard from "./VerticalCard";
import AcceptRequest from "./AcceptRequest";
export interface Friend {
  id: string;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  phoneNo: string;
  userCreationDate: string;
  countryOfResidence: string;
  city: string;
  state: string;
  address: string;
  postalCode: string;
  isPreferredMarketing: string;
  spotifyAccount: string;
}

export default function UserFriends() {
  const [searchVisible, setSearchVisible] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");
  const [isAcceptedFriend, setIsAcceptedFriend] = useState(false);
  const [pendingFriends, setPendingFriends] = useState<Friend[] | null>(null);

  useEffect(() => {
    async function fetchPendingFriends() {
      try {
        const response = await getPendingFriends();
        setPendingFriends(response.data?.friends);
      } catch (error) {
        console.error("Error fetching pending friends:", error);
      }
    }
    fetchPendingFriends();
  }, []);

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

  function handleAcceptRequest() {
    setIsAcceptedFriend(true);
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
              <PendingRequest
                username={username}
                firstName={firstName}
                lastName={lastName}
              />
            )}
          </div>
        )}
      </div>

      <div className="flex flex-col overflow-x-auto max-w-full mb-32">
        {/* {!isAcceptedFriend && (
          <AcceptRequest
            image="/images/ShawnMendes.jpg"
            firstName="Shawn"
            lastName="Mendes"
            onAcceptRequest={handleAcceptRequest}
          />
        )} */}
        {pendingFriends ? (
          pendingFriends.map((friend) => (
            <AcceptRequest
              key={friend.id}
              firstName={friend.firstName}
              lastName={friend.lastName}
              onAcceptRequest={handleAcceptRequest}
            />
          ))
        ) : (
          <div className="text-sm mb-6">
            No pending friend requests available.
          </div>
        )}
        <div className="flex gap-5">
          <VerticalCard
            image="/images/CharliePuth.jpg"
            firstName="Charlie"
            lastName="Puth"
          />
          {isAcceptedFriend && (
            <VerticalCard
              image="/images/ShawnMendes.jpg"
              firstName="Shawn"
              lastName="Mendes"
            />
          )}
        </div>
      </div>
    </div>
  );
}
