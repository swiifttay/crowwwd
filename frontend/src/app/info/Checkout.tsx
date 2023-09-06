import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";
import Paper from "@mui/material/Paper";
import * as React from "react";
import AddressForm from "./AddressForm";
import Button from "@mui/material/Button";

export default function Checkout() {
  const handleClick = () => {};

  return (
    <React.Fragment>
      <CssBaseline />
      <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
        <Paper
          variant="outlined"
          sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
          className="bg-theme-midnight rounded-lg"
        >
          <AddressForm />
          <Button
            variant="contained"
            onClick={handleClick}
            sx={{ mt: 3, ml: 1 }}
            className="font-mont bg-theme-light-blue"
          >
            Save
          </Button>
        </Paper>
      </Container>
    </React.Fragment>
  );
}
