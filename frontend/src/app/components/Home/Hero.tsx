import React from "react";
import { Video } from "@/app/components/Home";
import Image from "next/image";

export default function Hero() {
  return (
    <div className="flex items-center">
      {/* <Video /> */}
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
            <img src="/images/icons8-spotify.svg" alt="spotify image" />
          </a>
          <a
            href="https://twitter.com/home"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <img src="/images/icons8-twitterx.svg" alt="twitter x image" />
          </a>
          <a
            href="https://www.instagram.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <img src="/images/icons8-instagram.svg" alt="instagram image" />
          </a>
        </div>
      </div>
    </div>
  );
}
