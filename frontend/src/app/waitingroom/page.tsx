import { Box } from "@mui/material";
import Stepper from "../components/WaitingRoom/Stepper";
import CountdownTimer from "../components/WaitingRoom/CountdownTimer";

export default function WaitingRoom() {
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
          <CountdownTimer />
          <div className="font-bold text-5xl">27 minutes left</div>
        </div>
      </Box>
    </main>
  );
}
