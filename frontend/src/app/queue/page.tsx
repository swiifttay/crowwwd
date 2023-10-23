import { Box, Slider } from "@mui/material";
import Stepper from "../components/WaitingRoom/Stepper";
import "./style.css";

export default function Queue() {
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
          <div className="font-bold text-2xl mb-7">
            You are now in the queue.
          </div>
          <div className="font-bold text-7xl">2000+</div>
          <div className="font-bold text-lg mb-6">people ahead of you</div>
          <Slider defaultValue={10} aria-label="Default" />
        </div>
      </Box>
    </main>
  );
}
