"use client";

import { Box, Slider } from "@mui/material";
import Stepper from "../components/WaitingRoom/Stepper";
import "./style.css";

export default function Queue() {
  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper activeStep={2} />
      <Box
        sx={{
          width: "60%",
          height: "45%",
          backgroundColor: "#e2e8f0",
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <div className="text-black justify-center items-center align-middle flex flex-col">
          <div className="font-bold text-2xl mb-6">
            You are now in the queue.
          </div>
          <div className="font-bold text-7xl">2000+</div>
          <div className="font-bold text-lg mb-5">people ahead of you</div>
          <Slider defaultValue={10} aria-label="Default" />
        </div>
      </Box>
    </main>
  );
}
