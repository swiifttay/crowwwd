"use client";

import { Box } from "@mui/material";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import CountdownTimer from "../../../components/Queue/CountdownTimer";
import Stepper from "../../../components/Queue/Stepper";
import { useUserDetails } from "@/app/contexts/UserDetailsContext";
import { getCheckQueue, getWholeQueue } from "@/app/axios/queue";

export default function WaitingRoom({
  params,
}: {
  params: { eventId: string };
}) {
  const { eventId } = params;
  const { user } = useUserDetails();
  const router = useRouter();
  const [countdownFinished, setCountdownFinished] = useState(false);
  const [count, setCount] = useState(1);

  // const handleCountdownFinish = () => {
  //   setCountdownFinished(true);
  // };

  // useEffect(() => {
  //   if (countdownFinished) {
  //     router.push("/queue");
  //   }
  // }, [countdownFinished, router]);

  useEffect(() => {
    //const interval = setInterval(updateSlider, 1000);
    const fetchQueueStatus = async () => {
      if (user) {
        const status = await getCheckQueue(eventId);
        //console.log(status);
        setCount((prev) => prev + 1);
        if (status.statusName === "PENDING") {
          router.push(`/Ticket/${eventId}/queue`);
        }
        if (count === 2) {
          router.push(`/Ticket/${eventId}/queue`);
        }
      }
    };
    const interval = setInterval(fetchQueueStatus, 5000);

    return () => {
      console.log("hi");
      clearInterval(interval);
    };
  });

  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper activeStep={1} />
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
        <div className="text-white justify-center text-center items-center align-middle flex flex-col p-12">
          <div className="font-bold text-2xl mb-4">
            Thank you for joining the Waiting Room.
          </div>
          <div className="max-w-lg text-center mb-10">
            <div className="text-base">
              When the sale begins, your screen will automatically refresh and
              you will be moved into the Queue.
            </div>
          </div>
        </div>
      </Box>
    </main>
  );
}
