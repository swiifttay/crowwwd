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
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
        hero: "radial-gradient(circle at center, rgba(5, 16, 33, 0.7) 20%, rgba(7, 23, 47, 1) 70%), url('/images/home-6.jpg')",
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
