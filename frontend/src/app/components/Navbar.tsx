"use client";

import Image from "next/image";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import { Bars3Icon } from "@heroicons/react/24/outline";
import Link from "next/link";
import Logo from "/public/images/Logo.png";
import { useState } from "react";
import SidePanel from "./SidePanel";
import { useGlobalState } from "../contexts/globalStateContext";

export default function Navbar() {
  const { isOpen, setIsOpen } = useGlobalState();
  const {isAuthenticated} = useGlobalState();

  function toggleMenu() {
    setIsOpen(!isOpen);
  }
  return (
    <div className="w-full font-bold text-base py-[20px] md:px-[128px] flex items-center z-40 justify-between px-20">
      <a
        href="/explore"
        className="hover:text-gray-300 cursor-pointer hidden md:flex"
      >
        Explore
      </a>
      <div className="relative group hidden md:flex space-y-5">
        <a href="" className=" hover:text-gray-300">
          About Us
        </a>
        <div
          className="hidden group-hover:block w-40 bg-opacity-70 my-2
              transition-opacity duration-300 absolute z-10 py-1"
        >
          <ul>
            <li className="py-1">
              <a href="" className="hover:text-gray-300">
                Our Verticals
              </a>
            </li>
            <li>
              <a href="/faq" className="hover:text-gray-300">
                FAQs
              </a>
            </li>
          </ul>
        </div>
      </div>
      <Bars3Icon
        className="h-10 md:hidden cursor-pointer"
        onClick={toggleMenu}
      />

      <Link href="/">
        <Image src={Logo} alt="Logo" className="w-48 h-20 object-cover" />
      </Link>
      <div className="flex">
        <MagnifyingGlassIcon className="h-6 stroke-white hover:text-gray-300 cursor-pointer mr-2" />
        <div className="hover:text-gray-300 cursor-pointer">Search</div>
      </div>
      {isAuthenticated ? (
        <Link href="/userprofile" className="hidden md:flex">
          <div className="hover:text-gray-300 cursor-pointer">Profile</div>
        </Link>
      ) : (
        <Link href="/login" className="hidden md:flex">
          <div className="hover:text-gray-300 cursor-pointer">Login</div>
        </Link>
      )}
    </div>
  );
}
