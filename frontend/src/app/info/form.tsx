import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import "./style.css";

export default function AddressForm() {
  const inputStyles = {
    color: "white",
  };

  const sxStyles = {
    "& .MuiOutlinedInput-root": {
      "& fieldset": {
        borderColor: "theme-light-grey",
      },
      "&:hover fieldset": {
        borderColor: "white",
      },
      "&.Mui-focused fieldset": {
        borderColor: "white",
      },
    },
    "& label.Mui-focused": {
      color: "white",
    },
  };

  return (
    <>
      <Typography
        variant="h6"
        className="font-mont text-white mb-5"
        gutterBottom
      >
        Personal Information
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="firstName"
            label="First Name"
            fullWidth
            autoComplete="given-name"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="lastName"
            name="lastName"
            label="Last Name"
            fullWidth
            autoComplete="family-name"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="mobileNum"
            name="mobileNum"
            label="Mobile number"
            fullWidth
            autoComplete="mobile-number"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            sx={sxStyles}
            required
            id="address"
            name="address"
            label="Address line"
            fullWidth
            autoComplete="shipping address-line"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="city"
            name="city"
            label="City"
            fullWidth
            autoComplete="shipping address-level2"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            id="state"
            name="state"
            label="State/Province/Region"
            fullWidth
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="zip"
            name="zip"
            label="Zip / Postal code"
            fullWidth
            autoComplete="shipping postal-code"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            sx={sxStyles}
            required
            id="country"
            name="country"
            label="Country"
            fullWidth
            autoComplete="shipping country"
            variant="outlined"
            InputProps={{ style: inputStyles }}
            InputLabelProps={{ style: inputStyles }}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControlLabel
            control={
              <Checkbox
                color="primary"
                name="saveAddress"
                value="yes"
                className="text-white"
              />
            }
            label="Use this address for payment details"
            className="text-white font-mont"
          />
        </Grid>
      </Grid>
    </>
  );
}
