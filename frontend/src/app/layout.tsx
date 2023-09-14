import "./globals.css";
import type { Metadata } from "next";
import localFont from "next/font/local";
import Navbar from "./components/Navbar";

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
        <body className={`${mont.className} flex flex-col items-center w-full h-fit`}>
          <div className="flex flex-col items-center max-w-7xl w-full"><Navbar />{children}</div>
        </body>
    </html>
  );
}
