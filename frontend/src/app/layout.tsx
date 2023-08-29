import "./globals.css";
import type { Metadata } from "next";
// import { Inter } from "next/font/google";
import localFont from "next/font/local";

//const inter = Inter({ subsets: ["latin"] });
const mont = localFont({
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
  ],
  variable: "--font-mont",
});

// "../../public/fonts/Mont/Mont-Regular.otf",
//   weight: "400",
//   style: "normal",
//   variable: "--font-mont"

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
    <html lang="en">
      {/* <body className={inter.className}>{children}</body> */}
      <body className={mont.className}>{children}</body>
    </html>
  );
}
