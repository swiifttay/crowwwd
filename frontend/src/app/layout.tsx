import "./globals.css";
import type { Metadata } from "next";
import localFont from "next/font/local";
import Navbar from "./components/Navbar";

import SidePanel from "./components/SidePanel";
import Footer from "./components/Footer";
import { useState } from "react";
import { GlobalStateProvider, useGlobalState } from "./globalStateContext";

export const mont = localFont({
  src: [
    {
      path: "../../public/fonts/Mont/Mont-Regular.otf",
      weight: "400",
      style: "regular",
    },
    {
      path: "../../public/fonts/Mont/Mont-RegularItalic.otf",
      weight: "400",
      style: "italic",
    },
    {
      path: "../../public/fonts/Mont/Mont-Bold.otf",
      weight: "700",
      style: "bold",
    },
    {
      path: "../../public/fonts/Mont/Mont-SemiBold.otf",
      weight: "400",
      style: "semibold",
    },
    {
      path: "../../public/fonts/Mont/Mont-Light.otf",
      weight: "300",
      style: "light",
    },
  ],
  variable: "--font-mont",
});

export const metadata: Metadata = {
  title: "Crowwwd",
  description: "Created by the top 203 team",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
      <html>
        <body
          className={`${mont.className} flex flex-col items-center w-full h-fit`}
        >
          <GlobalStateProvider>
          <div className="flex flex-col items-start max-w-7xl w-full">
            <SidePanel />
            <Navbar />
            {children}
            <Footer />
          </div>
          </GlobalStateProvider>
        </body>
      </html>
  );
}
