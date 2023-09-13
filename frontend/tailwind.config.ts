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
      },

      fontFamily: {
        mont: "var(--font-mont)",
      },

      colors: {
        "theme-midnight": "#07172F",
        "theme-dark-blue": "#001C66",
        "theme-blue": "#0047FF",
        "theme-light-blue": "#1D90F4",
        "theme-grey": "#868B99",
        "theme-light-grey": "#C2C5CC",
      },
    },
  },
  plugins: [],
};
export default config;
