import Image from "next/image";
import { Navbar, Video, Hero } from "./components/Home/index";

export default function Home() {
  return (
    <main className="h-screen w-full px-24 my-20 bg-hero bg-cover items-center">
      <h1 className="font-bold text-7xl my-20">Your Ticket <br /> Paradigm</h1>
      {/* <Hero /> */}
      <div className="flex space-x-2 items-center">
          <a
            href="https://open.spotify.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <img src="/images/icons8-spotify.svg" alt="spotify image" width="30" height="30" />
          </a>
          <a
            href="https://twitter.com/home"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <img src="/images/icons8-twitterx.svg" alt="twitter x image" width="30" height="30" />
          </a>
          <a
            href="https://www.instagram.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer"
          >
            <img src="/images/icons8-instagram.svg" alt="instagram image" width="30" height="30" />
          </a>
        </div>
    </main>
  );
}
