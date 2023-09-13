"use client";

import Image from "next/image";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import Link from "next/link";
import Logo from "/public/images/Logo.png";

export default function Navbar() {
  return (
    <div className="w-full font-bold text-base py-[20px] md:px-[128px] justify-between flex items-center z-50 guide">
      <a href="/explore" className="hover:text-gray-300 cursor-pointer">Explore</a>
      <div className="relative group">
        <a href="" className=" hover:text-gray-300">
          About Us
        </a>
        <div
          className="hidden group-hover:block w-40 bg-opacity-70 my-2
              transition-opacity duration-300 absolute z-10"
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
        <Image src={Logo} alt="Logo" className="w-60 h-20 object-cover" />
      </Link>
      <div className="flex">
        <MagnifyingGlassIcon className="h-6 stroke-white hover:text-gray-300 cursor-pointer mr-2" />
        <div className="hover:text-gray-300 cursor-pointer">Search</div>
      </div>
      <Link href="/login">
        <div className="hover:text-gray-300 cursor-pointer">Login</div>
      </Link>
    </div>
  );
}
