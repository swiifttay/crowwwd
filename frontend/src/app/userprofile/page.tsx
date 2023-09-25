"use client"

import Image from "next/image";
import EventButtonShort from "./EventButtonShort";
import VerticalCard from "./VerticalCard";
import EventButtonLong from "./EventButtonLong";
import { StringLiteral } from "typescript";
import { getUserProfile } from "../axios/apiService";
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
  nationality: string;
  countryOfResidence: string;
  countryCode:string;
  gender:string;
  dateOfBirth:string;
  address: string;
  postalCode: string;
  isPreferredMarketing: string;
  spotifyAccount: string;
}


export default function UserProfile() {

  const [user, setUser] = useState<User>();

  useEffect(() => {
    fetchUser();
  }, [])
  

  const fetchUser = async () => {
    const response = await getUserProfile();
    setUser(response);
  }
  

  
  
  return (
    <div>
      <div className="flex flex-col justify-center items-center mt-4">
        <div className="flex gap-12">
          <div className="flex flex-col">
            <div className="flex gap-12">
              <div className="">
                <div className="text-3xl font-bold mt-8 mb-4">{user?.firstName} {user?.firstName}</div>
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
            <div className="text-xl font-bold mt-20 mb-4">
              Your favourite artists
            </div>
            <div className="flex gap-5">
              <VerticalCard
                image="/images/TaylorSwift.jpg"
                name="Taylor Swift"
              />
              <VerticalCard
                image="/images/TaylorSwift.jpg"
                name="Taylor Swift"
              />
              <VerticalCard
                image="/images/TaylorSwift.jpg"
                name="Taylor Swift"
              />
            </div>
          </div>

          <div className="ml-10">
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
        <div className="text-xl font-bold mt-16 mb-4">Friends</div>
        <div className="flex gap-5">
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
