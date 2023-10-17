import { Box, Button } from "@mui/material";
import Stepper from "../components/WaitingRoom/Stepper";

export default function WaitingRoom() {
  return (
    <main className="flex flex-col items-center w-full h-screen px-8">
      <Stepper />
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
          <div className="font-bold text-2xl mb-4">
            Tickets are now on sale.
          </div>
          <div className="text-lg">
            To shop for tickets, please join the Queue.
          </div>
          <div className="text-lg">We will provide additional information.</div>
          <button className="mt-4 w-2/3 bg-theme-blue text-white p-2 rounded-md hover:bg-theme-light-blue">
            Join the Queue
          </button>
          <div className="flex mt-6 text-sm">
            <div className="mr-1">
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
