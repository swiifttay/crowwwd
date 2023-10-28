"use client";

import type { NextPage } from "next";
import Head from "next/head";
import { useEffect, useState } from "react";
import { TimerContainer } from "./TimerContainer";
// import { TimerInput } from "./TimerInput";

const Home: NextPage = () => {
  const [time, setTime] = useState<number>(7);
  const [newTime, setNewTime] = useState<number>(0);
  const [hours, setHours] = useState<number>(0);
  const [minutes, setMinutes] = useState<number>(0);
  const [seconds, setSeconds] = useState<number>(0);

  const timeToDays = time * 60 * 60 * 24 * 1000;

  let countDownDate = new Date().getTime() + timeToDays;

  useEffect(() => {
    var updateTime = setInterval(() => {
      var now = new Date().getTime();

      var difference = countDownDate - now;

      var newHours = Math.floor(
        (difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
      );
      var newMinutes = Math.floor(
        (difference % (1000 * 60 * 60)) / (1000 * 60)
      );
      var newSeconds = Math.floor((difference % (1000 * 60)) / 1000);

      setHours(newHours);
      setMinutes(newMinutes);
      setSeconds(newSeconds);

      if (difference <= 0) {
        clearInterval(updateTime);
        setHours(0);
        setMinutes(0);
        setSeconds(0);
      }
    });

    return () => {
      clearInterval(updateTime);
    };
  }, [time]);

  const handleClick = () => {
    setTime(newTime);
    console.log(time);
    setNewTime(0);
  };

  const handleChange = (e: any) => {
    let inputTime = e.target.value;
    setNewTime(inputTime);
  };

  return (
    <div className="flex min-h-screen flex-col items-center bg-[#1e1f29]">
      <Head>
        <title>Launch Countdown Timer</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <TimerContainer hours={hours} minutes={minutes} seconds={seconds} />
      {/* <TimerInput
        value={newTime}
        handleClick={handleClick}
        handleChange={handleChange}
      /> */}
    </div>
  );
};

export default Home;
