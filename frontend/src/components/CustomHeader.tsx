"use client";

import Image from "next/image";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import { useState } from "react";
import Link from "next/link";
import RootLayout from "@/app/layout";
import Logo from "../../public/images/Logo.png"

export default function CustomHeader() {
  const [searchActivated, setsearchActivated] = useState(false);

  return (
    <RootLayout>
      <div className="w-full font-semibold sticky p-4 justify-between flex flex-grow bg-none z-50 top-0 text-xl">
        <div className="hover:text-gray-300 cursor-pointer px-5">Explore</div>
        <div className="relative group">
          <a href="" className=" hover:text-gray-300">
            About Us
          </a>
          <div
            className="hidden group-hover:block w-40 bg-opacity-70 my-2
              transition-opacity duration-300"
          >
            <ul>
              <li>
                <a href="" className="hover:text-gray-300">
                  Our Verticals
                </a>
              </li>
              <li>
                <a href="" className="hover:text-gray-300">
                  FAQs
                </a>
              </li>
            </ul>
          </div>
        </div>
        <Link href="/">
          {/* <Image
            src={Logo}
            alt="Logo"
            className="w-20"
          /> */}
        </Link>
        <MagnifyingGlassIcon className="h-7 hover:text-gray-300 cursor-pointer" />
        <Link href="/login">
          <div className="hover:text-gray-300 cursor-pointer px-5">
            Login/Register
          </div>
        </Link>
      </div>
    </RootLayout>
  );
}
