import Image from "next/image";
import { Customheader, Video, Hero } from "@/components";

export default function Home() {
  return (
    <main className="overflow-hidden">
      <Customheader />
      <Hero />
    </main>
  );
}
