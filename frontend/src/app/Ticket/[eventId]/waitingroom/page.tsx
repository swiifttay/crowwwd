"use client";

import { Box } from "@mui/material";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import CountdownTimer from "../../../components/Queue/CountdownTimer";
import Stepper from "../../../components/Queue/Stepper";
import { useUserDetails } from "@/app/contexts/UserDetailsContext";
import { getCheckQueue, getWholeQueue } from "@/app/axios/queue";

export default function WaitingRoom({params} : {params: {eventId:string}}) {
  const {eventId} = params;
  const {user} = useUserDetails();
  const router = useRouter();
  const [countdownFinished, setCountdownFinished] = useState(false);

  const handleCountdownFinish = () => {
    setCountdownFinished(true);
  };

  useEffect(() => {
    if (countdownFinished) {
      router.push("/queue");
    }
  }, [countdownFinished, router]);

  useEffect(() => {
    //const interval = setInterval(updateSlider, 1000);

    const fetchQueueStatus = async () => {
      if (user) {
        const status = await getCheckQueue(user, eventId);
        if (status )
      }
    };

  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper activeStep={1} />
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
        <div className="text-black justify-center text-center items-center align-middle flex flex-col p-12">
          <div className="font-bold text-2xl mb-4">
            Thank you for joining the Waiting Room
          </div>
          <div className="max-w-lg text-center mb-10">
            <div className="text-lg">
              When the sale begins, your screen will automatically refresh and
              you will be moved into the Queue.
            </div>
          </div>
        </div>
      </Box>
    </main>
  );
}
