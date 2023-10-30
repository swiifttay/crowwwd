"use client"

import { Box } from "@mui/material";
import Stepper from "../components/WaitingRoom/Stepper";
import { useRouter } from "next/navigation";

export default function Lobby() {
  const router = useRouter();
  const handleJoinQueue = () => {
    router.push("/waitingroom");
  };

  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper activeStep={0} />
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
            Tickets are now on sale.
          </div>
          <div className="max-w-sm text-center">
            <div className="text-lg">
              To shop for tickets, please join the Queue. We will provide
              additional information.
            </div>
          </div>
          <button className="mt-4 w-2/3 bg-theme-blue text-white p-2 rounded-md hover:bg-theme-light-blue" onClick={handleJoinQueue}>
            Join the Queue
          </button>
          <div className="mt-6 text-sm flex flex-col sm:flex-row">
            <div className="sm:mr-1">
              Availability and pricing are subject to change.
            </div>
            <div className="text-theme-blue hover:text-theme-light-blue hover:underline cursor-pointer">
              Learn more.
            </div>
          </div>
        </div>
      </Box>
    </main>
  );
}
