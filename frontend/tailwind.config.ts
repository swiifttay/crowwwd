import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic": "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
        "hero": "radial-gradient(circle at center, rgba(5, 16, 33, 0.4) 20%, rgba(7, 23, 47, 1) 55%), url('/images/home-1.jpg')",
        "hollow-purple": "linear-gradient(to right, rgba(5, 16, 33, 0.5) 1%, rgba(0, 36, 128, 0.8) 90%), url('/images/hollow-purple.jpg')",
        "space": "radial-gradient(circle at bottom, rgba(7, 23, 47, 0.1) 10%, rgba(7, 23, 47, 1) 23%), url('/images/space.jpg')",
        "azure": "linear-gradient(to right, rgba(0, 0, 0, 0.2) 10%, rgba(0, 0, 0, 0.3) 90%), url('/images/azure.jpg')",
      },

      fontFamily: {
        mont: "var(--font-mont)",
      },

      colors: {
        "theme-midnight": "#07172F",
        "theme-blue": "#0047FF",
        "theme-blue-10": "#0040e6",
        "theme-blue-20": "#0039cc",
        "theme-blue-30": "#0032b3",
        "theme-blue-40": "#002b99",
        "theme-blue-50": "#002480",
        "theme-blue-60": "#001c66",
        "theme-light-blue": "#1D90F4",
        "theme-grey": "#868B99",
        "theme-offwhite": "#F3EFE0",
      },
    },
  },
  plugins: [],
};
export default config;

// 106,116,130