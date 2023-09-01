import Image from "next/image";
import { CustomHeader, Video, Hero } from "./components/index";

export default function Home() {
  return (
    <main className="overflow-hidden">
      <CustomHeader />
      <Hero />
    </main>
  );
}
