"use client"

import Image from "next/image";
import EventButtonShort from "./EventButtonShort";
import VerticalCard from "./VerticalCard";
import EventButtonLong from "./EventButtonLong";
import { StringLiteral } from "typescript";
import { getFanRecords, getUserProfile, getArtistById, getSpotifyLogin, updateFanRecords } from "../axios/apiService";
import { useEffect, useState } from "react";

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
  const [fanRecords, setFanRecords] = useState<FanRecord[] | undefined>(undefined);
  const [favArtist, setFavArtist] = useState<Artist[] | undefined>(undefined);


  useEffect(() => {
    fetchUser();
    fetchFanRecords();
  }, [])


  const fetchUser = async () => {
    try {
      const response = await getUserProfile();
      setUser(response?.data.user);
    } catch (error) {
      console.log({ error });
    }
  }

  const handleSpotifyButton = async () => {

    const response = await updateFanRecords();
    console.log(response);
    if (!response) {
      try {
        const responseGetAccount = await getSpotifyLogin();
        window.location.replace(responseGetAccount?.data);
      } catch (error) {
        console.log(error);
      }
    } else {
      
      location.reload();
    }

  }

  const fetchFanRecords = async () => {
    try {
      const response = await getFanRecords();
      const fanRecordsData: FanRecord[] | undefined = response?.data.allFanRecords;

      if (fanRecordsData) {
        setFanRecords(fanRecordsData);

        const artistResponses = await Promise.all(
          fanRecordsData.map(async (fanRecord: FanRecord) => {
            const artistResponse = await getArtistById(fanRecord.artistId);
            return artistResponse?.data.artist;
          })
        );

        const flattenedArtistResponses = artistResponses.flat();

        setFavArtist((prev: Artist[] | undefined) => {
          const updatedFavArtist: Artist[] = prev ? [...prev] : [];

          // Add the artist responses to the existing list
          updatedFavArtist.push(...flattenedArtistResponses);
          console.log(updatedFavArtist.length);

          return updatedFavArtist;
        });
      }
    } catch (error) {
      console.error('Error fetching fan records:', error);
    }
  };

  return (
    <div>
      <div className="flex flex-col justify-center  mt-4 w-full">
        <div className="flex flex-row ">
          <div className="flex flex-col w-2/3">
            <div className="flex gap-12">
              <div className="">
                <div className="text-3xl font-bold mt-8 mb-4">{user?.firstName} {user?.lastName}</div>
                <div className="text-md">{user?.username}</div>
                <div className="text-md">{user?.email}</div>
                <div className="mt-6 hover:underline hover:text-sky-400 text-theme-light-blue cursor-pointer">
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
              <button className="bg-green-900 hover:bg-green-800 text-white text-center px-6 py-2 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]"
                onClick={handleSpotifyButton}>
                Connect to Spotify
              </button>
            </div>
            <div className="flex overflow-x-auto max-w-full">

              <div className="flex gap-5 overflow-x-auto max-w-2xl h-full px-4 py-4">
                {favArtist?.length !== 0 ? (
                  favArtist?.slice(0, Math.min(10, favArtist.length)).map((artist, i) => {
                    console.log(artist);
                    return (
                      <VerticalCard key={i} image={artist.artistImageURL} name={artist.name} />
                    );
                  })
                ) : (
                  <p>No favorite artists available.</p>
                )}
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
      <div className="">
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
        </div>
      </div>

      <div className="">
        <div className="text-xl font-bold mb-4 mt-16">Friends</div>
        <div className="flex gap-5 mb-32">
          <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
          <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
          <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
          <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
          <VerticalCard image="/images/TaylorSwift.jpg" name="Taylor Swift" />
        </div>
      </div>
    </div>
  );
}
