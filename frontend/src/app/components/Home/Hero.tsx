import React from "react";
import { Video } from "@/app/components/Home";
import Image from "next/image";

export default function Hero() {
  return (
    <div className="flex items-center">
      <Video />
      <div className="px-5 mx-10 z-50 space-y-5">
        <h1 className="font-mont font-bold text-4xl">
          Your new paradigm to ticketing
        </h1>
        <div className="flex space-x-2">
          <a
            href="https://open.spotify.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <Image
              src="/icons8-spotify-50.png"
              alt="spotify icon"
              width={50}
              height={50}
            />
          </a>
          <a
            href="https://twitter.com/home"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <Image
              src="/icons8-twitterx-50.png"
              alt="twitterx icon"
              width={50}
              height={50}
            />
          </a>
          <a
            href="https://www.instagram.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <Image
              src="/icons8-instagram-50.png"
              alt="instagram icon"
              width={50}
              height={50}
            />
          </a>
        </div>
      </div>
    </div>
  );
}
