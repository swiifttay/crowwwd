import * as React from "react";
import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import "./Stepper.css";

const steps = ["The Lobby", "Waiting Room", "Queue", "Pick Your Seats"];

export default function HorizontalLinearAlternativeLabelStepper() {
  return (
    <Box sx={{ width: "100%" }}>
      <Stepper activeStep={1} alternativeLabel>
        {steps.map((label) => (
          <Step
            key={label}
            sx={{ "&.MuiStepLabel-labelContainercolor": { color: "white" } }}
          >
            <StepLabel sx={{ "&.MuiStepLabel-root": { color: "white" } }}>
              {label}
            </StepLabel>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
}

// "use client"

// import Box from "@mui/material/Box";
// import Stepper from "@mui/material/Stepper";
// import Step from "@mui/material/Step";
// import StepLabel from "@mui/material/StepLabel";
// import { styled } from "@mui/system";

// const steps = [
//   "Select master blaster campaign settings",
//   "Create an ad group",
//   "Create an ad",
// ];

// const StyledStepLabel = styled(StepLabel)({
//   "&.MuiStepLabel-root": {
//     color: "white",
//   },
// });

// export default function HorizontalLinearAlternativeLabelStepper() {
//   return (
//     <Box sx={{ width: "100%" }}>
//       <Stepper activeStep={1} alternativeLabel>
//         {steps.map((label) => (
//           <Step key={label}>
//             <StyledStepLabel>{label}</StyledStepLabel>
//           </Step>
//         ))}
//       </Stepper>
//     </Box>
//   );
// }

// import Box from "@mui/material/Box";
// import Stepper from "@mui/material/Stepper";
// import Step from "@mui/material/Step";
// import StepLabel from "@mui/material/StepLabel";

// const steps = [
//   "Select master blaster campaign settings",
//   "Create an ad group",
//   "Create an ad",
// ];

// export default function HorizontalLinearAlternativeLabelStepper() {
//   return (
//     <Box sx={{ width: "100%" }}>
//       <Stepper activeStep={1} alternativeLabel>
//         {steps.map((label) => (
//           <Step key={label}>
//             <StepLabel style={{ color: "white" }}>{label}</StepLabel>
//           </Step>
//         ))}
//       </Stepper>
//     </Box>
//   );
// }
