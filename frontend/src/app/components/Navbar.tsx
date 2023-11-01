"use client";

import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import { Bars3Icon } from "@heroicons/react/24/outline";
import Image from "next/image";
import Link from "next/link";
import { useState } from "react";
import { useGlobalState } from "../globalStateContext";
import styles from "./Navbar.module.css";
import Logo from "/public/images/Logo.png";
import { useAuth } from "../AuthContext";

export default function Navbar() {
  const { isOpen, setIsOpen } = useGlobalState();
  // const [isLogin, setIsLogin] = useState(false);
  const { isLogin } = useAuth();
  const [searchVisible, setSearchVisible] = useState(false);
  const [searchText, setSearchText] = useState("");

  function toggleMenu() {
    setIsOpen(!isOpen);
  }

  function handleSearchClick() {
    setSearchVisible(true);
  }

  function handleSearchInputChange(event) {
    setSearchText(event.target.value);
  }

  function handleSearchSubmit(event) {
    event.preventDefault();

    // Perform the search or navigation logic here based on the searchText
    // For example, you can redirect the user to a search results page.
    // router.push(`/search?query=${searchText}`);
  }

  // function handleLoginSuccess() {
  //   setIsLogin(true);
  // }

  return (
    <div className="w-full font-bold text-base py-[20px] md:px-[128px] flex items-center z-40 justify-between px-20">
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

      <Link href="/">
        <Image src={Logo} alt="Logo" className="w-48 h-20 object-cover" />
      </Link>

      <div className="flex">
        <div
          className={`hover-text-gray-300 cursor-pointer ${
            searchVisible ? "hidden" : ""
          }`}
          onClick={handleSearchClick}
        >
          Search Friend
        </div>

        {searchVisible ? (
          <div className=""> {/*{styles.searchContainer}*/}
            <form className={styles.searchForm} onSubmit={handleSearchSubmit}>
              <input
                type="text"
                value={searchText}
                onChange={handleSearchInputChange}
                placeholder="Search..."
                className={styles.searchInput}
              />
              <button type="submit" className={styles.searchButton}>
                <MagnifyingGlassIcon className="h-6 stroke-white hover-text-gray-300 cursor-pointer" />
              </button>
            </form>
          </div>
        ) : null}
      </div>

      {isLogin ? (
        <Link href="/userprofile" className="hidden md:flex">
          <div className="hover-text-gray-300 cursor-pointer">Profile</div>
        </Link>
      ) : (
        <Link href="/login" className="hidden md:flex">
          <div className="hover-text-gray-300 cursor-pointer">Login</div>
        </Link>
      )}
    </div>
  );
}
