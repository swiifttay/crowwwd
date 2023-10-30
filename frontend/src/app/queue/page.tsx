"use client";

import { Box, Slider } from "@mui/material";
import { useEffect, useState } from "react";
import Stepper from "../components/WaitingRoom/Stepper";
import "./style.css";

export default function Queue() {
  const [sliderValue, setSliderValue] = useState(5);
  const [peopleCount, setPeopleCount] = useState("2000+");

  const updateSlider = () => {
    setSliderValue((prevValue) => {
      const newValue = prevValue + 1;
      return newValue > 100 ? 0 : newValue;
    });
  };

  useEffect(() => {
    const interval = setInterval(updateSlider, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  useEffect(() => {
    if (sliderValue >= 0 && sliderValue < 20) {
      setPeopleCount("2000+");
    } else if (sliderValue >= 20 && sliderValue < 70) {
      setPeopleCount("1000+");
    } else if (sliderValue >= 70 && sliderValue < 80) {
      setPeopleCount("500+");
    } else {
      setPeopleCount("100+");
    }
  }, [sliderValue]);

  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper activeStep={2} />
      <Box
        sx={{
          width: "auto",
          height: "auto",
          backgroundColor: "#e2e8f0",
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <div className="text-black justify-center text-center items-center align-middle flex flex-col p-12 lg:px-28 pb-16">
          <div className="font-bold text-2xl mb-6">
            You are now in the queue.
          </div>

          <div className="font-bold text-8xl">{peopleCount}</div>
          <div className="font-bold text-lg mb-5">people ahead of you</div>
          <Slider value={sliderValue} aria-label="Default" />
        </div>
      </Box>
    </main>
  );
}
