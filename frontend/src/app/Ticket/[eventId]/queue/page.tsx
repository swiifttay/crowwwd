"use client";

import { Box, Slider } from "@mui/material";
import { ChangeEvent, useEffect, useState } from "react";
import Stepper from "../../../components/Queue/Stepper";
import "./style.css";
import {
  getCheckQueue,
  getWholeQueue,
} from "../../../axios/queue";
import { useUserDetails } from "@/app/contexts/UserDetailsContext";
import {useRouter} from "next/navigation";

export default function Queue({ params }: { params: { eventId: string } }) {
  const {eventId} = params;
  //find location in the queue
  const { user } = useUserDetails();
  const router = useRouter();
  const [sliderValue, setSliderValue] = useState(0);
  const [frontCount, setFrontCount] = useState(0);
  const [totalCount, setTotalCount] = useState(5);

  const [peopleCount, setPeopleCount] = useState("");

  const updateSlider = () => {
    setSliderValue((prevValue) => {
      const newValue = prevValue + 1;
      return newValue > 100 ? 0 : newValue;
    });
  };

  //[EDIT] Make an api call every 10 seconds to get an update from the server
  // useEffect(() => {
  //   //const interval = setInterval(updateSlider, 1000);

  //   const fetchQueueStatus = async () => {
  //     if (user) {
  //       const status = await getCheckQueue(eventId);
  //       console.log(status);
  //     }
  //     if (frontCount === 0) {
  //       router.push(`/Ticket/${eventId}/seats`);
  //     }
  //     setFrontCount(prev => prev - 1)
  //     const res = await getWholeQueue(eventId);
  //     console.log(res);
  //     setFrontCount(res.countBefore ?? 0);
  //     setTotalCount(res.countPending);
  //   };

  //   const interval = setInterval(fetchQueueStatus, 3000);

  //   return () => {
  //     clearInterval(interval);
  //   };
  // },[]);

  useEffect(() => {
    const interval = setInterval(() => {
      if (sliderValue < 5) {
        setSliderValue(sliderValue + 1);
      } else if (sliderValue === 5) {
        router.push(`/Ticket/${eventId}/seats`);
      }
    }, 3000);

    return () => {
      clearInterval(interval);
    };
  }, [sliderValue]);

  //[EDIT] Make the slider reflect relative position in queue
  useEffect(() => {
    setSliderValue(frontCount);
    if (frontCount > 100) {
      setPeopleCount("100+")
    } else if (frontCount > 500) {
      setPeopleCount("500+")
    } else if (frontCount > 1000) {
      setPeopleCount("1000+")
    } else {
      setPeopleCount(sliderValue + "")
    }
  }, [frontCount]);

  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper activeStep={2} />
      <Box
        sx={{
          width: "auto",
          height: "auto",
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          borderRadius: "20px",
          backgroundImage:
            "linear-gradient(to right, #f44369 10%, #3e3b92 95%)",
          boxShadow:
            "0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1)",
        }}
      >
        <div className="text-white justify-center text-center items-center align-middle flex flex-col p-12 lg:px-28 pb-16">
          <div className="font-bold text-2xl mb-6">
            You are now in the queue.
          </div>

          <div className="font-bold text-8xl">{5 - sliderValue}</div>
          <div className="font-bold text-lg mb-5">People Ahead Of You</div>
          <Slider
            className=" text-theme-blue-20"
            value={sliderValue}
            min={0}
            max={5}
            step={2}
            aria-label="Default"
          />
        </div>
      </Box>
    </main>
  );
}
