"use client";

import { Bars3Icon } from "@heroicons/react/24/outline";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useGlobalState } from "../globalStateContext";
import Logo from "/public/images/Logo.png";

const Navbar = () => {
  const { isOpen, setIsOpen } = useGlobalState();
  const [isLogin, setIsLogin] = useState(false);

  const checkLoginStatus = async () => {
    const token = localStorage.getItem("token");
    setIsLogin(Boolean(token));
  };

  useEffect(() => {
    checkLoginStatus();
  }, []);

  function toggleMenu() {
    setIsOpen(!isOpen);
  }

  return (
    <div className="w-full font-bold text-base py-[20px] md:px-[128px] flex items-center z-40 justify-between px-20">
      <Link href="/">
        <Image src={Logo} alt="Logo" className="w-48 h-20 object-cover" />
      </Link>

      <a
        href="/explore"
        className="hover:text-gray-300 cursor-pointer hidden md:flex"
      >
        Explore
      </a>

      <div className="relative group hidden md:flex space-y-5">
        <a href="" className="hover-text-gray-300">
          About Us
        </a>
        <div className="hidden group-hover:block w-40 bg-opacity-70 my-2 transition-opacity duration-300 absolute z-10">
          <ul>
            <li>
              <a href="" className="hover-text-gray-300">
                Our Verticals
              </a>
            </li>
            <li>
              <a href="" className="hover-text-gray-300">
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

      {isLogin ? (
        <Link href="/userprofile" className="hidden md:flex">
          <div className="hover-text-gray-300 cursor-pointer pr-16">
            Profile
          </div>
        </Link>
      ) : (
        <Link href="/login" className="hidden md:flex">
          <div className="hover-text-gray-300 cursor-pointer pr-16">Login</div>
        </Link>
      )}
    </div>
  );
};

export default Navbar;
