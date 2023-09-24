import React, { useState } from "react";
import RegisterForm from "../register/RegisterForm";
import InfoForm from "./form";

function ParentComponent() {
  const [registerDetails, setRegisterDetails] = useState({
    firstName: "",
    lastName: "",
    phoneNo: "",
    address: "",
    nationality: "", //city
    countryCode: "", //state
    postalCode: "",
    countryOfResidence: "",

    username: "",
    password: "",
    email: "",
    confirmPassword: "",

    gender: "A",
    dateOfBirth: "A",
  });

  return (
    <div>
      <RegisterForm
        registerDetails={registerDetails}
        setRegisterDetails={setRegisterDetails}
      />
      <InfoForm registerDetails={registerDetails} />
    </div>
  );
}

export default ParentComponent;
