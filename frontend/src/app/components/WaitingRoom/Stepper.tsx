import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import React from "react";
import "./Stepper.css";

const steps = ["The Lobby", "Waiting Room", "Queue", "Pick Your Seats"];
interface StepperProps {
  activeStep: number;
}

export default function HorizontalLinearAlternativeLabelStepper({
  activeStep,
}: StepperProps) {
  return (
    <Box sx={{ width: "100%" }}>
      <Stepper activeStep={activeStep} alternativeLabel>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
}
