"use client";

import { useEffect, useState } from "react";
import { TimerContainer } from "./TimerContainer";

const CountdownTimer = ({ onCountdownFinish }) => {
  const [time, setTime] = useState<number>(0.0001);
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
        onCountdownFinish();
      } else if (difference <= 2000) {
        onCountdownFinish();
      }
    });

    // return () => {
    //   clearInterval(updateTime);
    // };
  }, [time]);

  // const handleClick = () => {
  //   setTime(newTime);
  //   console.log(time);
  //   setNewTime(0);
  // };

  // const handleChange = (e: any) => {
  //   let inputTime = e.target.value;
  //   setNewTime(inputTime);
  // };

  return (
    <div className="">
      <TimerContainer hours={hours} minutes={minutes} seconds={seconds} />
    </div>
  );
};

export default CountdownTimer;
