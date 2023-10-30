"use client";

import Image from "next/image";
import EventButtonShort from "./EventButtonShort";
import VerticalCard from "./VerticalCard";
import EventButtonLong from "./EventButtonLong";
import { StringLiteral } from "typescript";
import {
  getFanRecords,
  getUserProfile,
  getArtistById,
  getSpotifyLogin,
  updateFanRecords,
  getSpotifyToken,
} from "../axios/apiService";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import React from "react";

export interface User {
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
  // dateOfBirth: string;
  address: string;
  postalCode: string;
  isPreferredMarketing: string;
  spotifyAccount: string;
}

export interface FanRecord {
  id: string;
  userId: string;
  artistId: string;
  registerDate: string;
}

export interface Artist {
  id: string;
  name: string;
  website: string;
  artistImage: string;
  artistImageURL: string;
  description: string;
}

export default function UserProfile() {
  const [user, setUser] = useState<User>();
  const [fanRecords, setFanRecords] = useState<FanRecord[]>();
  const [favArtist, setFavArtist] = useState<Artist[]>();

  const [msg, setMsg] = useState("");
  const [isLoggedInSpotify, setIsLoggedInSpotify] = useState(false);
  const [isArtistLoaded, setIsArtistLoaded] = useState(false);
  const [spotifyButtonMsg, setSpotifyButtonMsg] = useState("");

  const router = useRouter();

  useEffect(() => {
    if (!localStorage.getItem("token")) {
      router.push("/login");
    }
    checkSpotifyLoginStatus();
    fetchUser();
    fetchFanRecords();
    // console.log(fanRecords);
  }, []);

  const fetchUser = async () => {
    const response = await getUserProfile();

    if (response.request?.status === 200) {
      setUser(response.data.user);
    } else {
      router.push("/login");
    }
  };

  const checkSpotifyLoginStatus = async () => {
    const spotifyTokenResponse = await getSpotifyToken();
    console.log(spotifyTokenResponse.data?.response);
    if (
      spotifyTokenResponse?.status === 200 &&
      spotifyTokenResponse.data?.response != null
    ) {
      console.log("success");
      localStorage.setItem("spotifyToken", spotifyTokenResponse.data.response);
      setSpotifyButtonMsg("Update My Records");
      setIsLoggedInSpotify(true);
    } else {
      console.log("failure");
      setSpotifyButtonMsg("Connect to Spotify");
      setIsLoggedInSpotify(false);
    }
  };

  const handleSpotifyButton = async () => {
    if (isLoggedInSpotify) {
      const updateFanRecordsResponse = await updateFanRecords();
      if (updateFanRecordsResponse.request?.status === 200) {
        window.location.reload();
      } else {
        localStorage.removeItem("spotifyToken");
        setIsLoggedInSpotify(false);
        setSpotifyButtonMsg("Connect to Spotify");
      }
    } else {
      const getAccountResponse = await getSpotifyLogin();
      if (getAccountResponse.request?.status == 200) {
        window.location.replace(getAccountResponse?.data);
      } else {
        if (!localStorage.getItem("token")) {
          router.push("/login");
        }
      }
    }
  };

  const fetchFanRecords = async () => {
    try {
      const response = await getFanRecords();

      // check if it user has logged to spotify
      if (response.status == 200) {
        const fanRecordsData: FanRecord[] = response.data.allFanRecords;

        // console.log(fanRecordsData.length);
        // if there is any fanrecord data
        if (fanRecordsData.length !== 0) {
          setFanRecords(fanRecordsData);

          // get all the artists that are relevant
          const artistResponses = await Promise.all(
            fanRecordsData.map(async (fanRecord: FanRecord) => {
              const artistResponse = await getArtistById(fanRecord.artistId);
              return artistResponse?.data.artist;
            }),
          );
          const flattenedArtistResponses = artistResponses.flat();

          setFavArtist((prev: Artist[] | undefined) => {
            const updatedFavArtist: Artist[] = prev ? [...prev] : [];

            // Add the artist responses to the existing list
            updatedFavArtist.push(...flattenedArtistResponses);
            setIsArtistLoaded(true);

            return updatedFavArtist;
          });
        } else {
          setMsg("Connect to Spotify to see your favourite artists!");
        }
      } else {
        setMsg("Connect to Spotify to see your favourite artists!");
      }
    } catch (error) {
      console.error("Error fetching fan records:", error);
    }
  };

  const handleUpdateProfile = async () => {
    router.push("/updateprofile");
  };

  return (
    <main className="flex flex-col items-center h-fit relative w-full px-8">
      <div className="flex flex-col justify-center align-center mt-4 w-full">
        <div className="flex flex-row ">
          <div className="flex flex-col sm:w-full lg:w-2/3">
            <div className="flex gap-12">
              <div className="">
                <div className="text-3xl font-bold mt-8 mb-4">
                  {user?.firstName} {user?.lastName}
                </div>
                <div className="text-md">{user?.username}</div>
                <div className="text-md">{user?.email}</div>
                <div
                  className="mt-6 hover:underline hover:text-sky-400 text-theme-light-blue cursor-pointer"
                  onClick={handleUpdateProfile}
                >
                  Update Profile
                </div>
              </div>

              <div className="">
                <Image
                  src="/images/Siyu.png"
                  alt="Profile Picture"
                  className="rounded-full"
                  width={200}
                  height={200}
                />
              </div>
            </div>
            <div className="flex flex-row justify-between mb-4 mt-20">
              <div className="text-xl font-bold w-1/2">
                Your favourite artists
              </div>
              <button
                className="bg-green-900 hover:bg-green-800 text-white text-center px-6 py-2 rounded-lg"
                onClick={handleSpotifyButton}
              >
                {spotifyButtonMsg}
              </button>
            </div>
            <div className="flex overflow-x-auto max-w-full">
              <div className="flex gap-5 overflow-x-auto max-w-2xl h-full px-4 py-8">
                <div className={`${isArtistLoaded ? "hidden" : "display"}`}>
                  {" "}
                  Loading...{" "}
                </div>
                {favArtist
                  ?.slice(0, Math.min(10, favArtist.length))
                  .map((artist, i) => {
                    return (
                      <VerticalCard
                        key={i}
                        image={artist.artistImageURL}
                        name={artist.name}
                      />
                    );
                  })}
              </div>
            </div>
          </div>

          <div className="ml-10 w-1/3">
            <div className="text-xl font-bold mt-6 mb-4">What you may like</div>
            <div className="flex flex-col gap-3">
              <EventButtonShort
                image="/images/TaylorSwift.jpg"
                title="Reputation Tour"
                artist="Taylor Swift"
              />
              <EventButtonShort
                image="/images/TaylorSwift.jpg"
                title="Reputation Tour"
                artist="Taylor Swift"
              />
              <EventButtonShort
                image="/images/TaylorSwift.jpg"
                title="Reputation Tour"
                artist="Taylor Swift"
              />
              <EventButtonShort
                image="/images/TaylorSwift.jpg"
                title="Reputation Tour"
                artist="Taylor Swift"
              />
              <EventButtonShort
                image="/images/TaylorSwift.jpg"
                title="Reputation Tour"
                artist="Taylor Swift"
              />
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-col w-full">
        <div className="text-xl font-bold mt-16 mb-4">
          Your purchased concerts
        </div>
        <div className="flex flex-col gap-3">
          <EventButtonLong
            image="/images/TaylorSwift.jpg"
            title="Reputation Tour"
            artist="Taylor Swift"
            datetime="Fri 15 Sep 2023, 7pm"
            venue="The Star Theatre, The Star Performing Arts Centre"
          />
          <EventButtonLong
            image="/images/TaylorSwift.jpg"
            title="Reputation Tour"
            artist="Taylor Swift"
            datetime="Fri 15 Sep 2023, 7pm"
            venue="The Star Theatre, The Star Performing Arts Centre"
          />
          <EventButtonLong
            image="/images/TaylorSwift.jpg"
            title="Reputation Tour"
            artist="Taylor Swift"
            datetime="Fri 15 Sep 2023, 7pm"
            venue="The Star Theatre, The Star Performing Arts Centre"
          />
          <EventButtonLong
            image="/images/TaylorSwift.jpg"
            title="Reputation Tour"
            artist="Taylor Swift"
            datetime="Fri 15 Sep 2023, 7pm"
            venue="The Star Theatre, The Star Performing Arts Centre"
          />
        </div>
      </div>

      <div className="flex flex-col w-full">
        <div className="text-xl font-bold mb-4 mt-16">Friends</div>
        <div className="flex overflow-x-auto max-w-full mb-32 px-4">
          <div className="flex gap-5">
            <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
            <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
            <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
            <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
            <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
          </div>
        </div>
      </div>
    </main>
  );
}
