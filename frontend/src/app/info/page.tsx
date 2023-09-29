// "use client";

// import Button from "@mui/material/Button";
// import Container from "@mui/material/Container";
// import Paper from "@mui/material/Paper";
// import AddressForm from "./form";
// import ParentComponent from "./ParentComponent";

// function Info() {

//   return (
//     <div className="flex flex-col items-center justify-center w-full text-center">
//       <>
//         <Container component="main" maxWidth="sm">
//           <Paper
//             variant="outlined"
//             sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
//             className="rounded-lg bg-gradient-to-b from-theme-dark-blue to-theme-midnight"
//           >
//             {/* <AddressForm /> */}
//             <ParentComponent />
//           </Paper>
//         </Container>
//       </>
//     </div>
//   );
// }

// export default Info;

// // "use client";

// // import React, { useState } from "react";
// // import PhoneInput from "react-phone-input-2";
// // import "react-phone-input-2/lib/style.css";
// // import { COUNTRIES } from "../components/CountryPicker/countries";
// // import CountrySelector from "../components/CountryPicker/selector";
// // import { inputFields } from "./config";
// // import Checkout from "./Checkout";

// // function Info() {
// //   const myRef = React.createRef<HTMLDivElement>();

// //   const [isOpen, setIsOpen] = useState(false);
// //   const [country, setCountry] = useState("SG");

// //   const [phoneNumber, setPhoneNumber] = useState("");
// //   const [valid, setValid] = useState(true);

// //   const handleChange = (value) => {
// //     setPhoneNumber(value);
// //     setValid(validatePhoneNumber(value));
// //   };

// //   const validatePhoneNumber = (phoneNumber) => {
// //     const phoneNumberPattern = /^\d(10)$/;
// //     return phoneNumberPattern.test(phoneNumber);
// //   };

// //   return (
// //     <div className="flex flex-col items-center justify-center min-h-screen py-2">
// //       <main className="flex relative flex-col justify-center w-full flex-1 px-20 text-center">
// //       <Checkout />
// //         <form className="mt-8 w-full max-w-sm">

// //           <div className="flex flex-col items-center justify-center h-50vh text-black">
// //             {/*https://morioh.com/a/2d3761b299fd/highly-customizable-phone-input-component-with-auto-formatting*/}
// //             <PhoneInput
// //               country={"sg"}
// //               // regions={'asia'}
// //               value={phoneNumber}
// //               preferredCountries={["sg"]}
// //               onChange={handleChange}
// //               countryCodeEditable={false}
// //               enableSearch
// //               disableSearchIcon
// //               searchPlaceholder="Search"
// //               inputProps={{ required: true }}
// //               inputStyle={{ textAlign: "left" }}
// //               searchStyle={{ alignItems: "start", textAlign: "start" }}
// //             />
// //             {!valid && (
// //               <p className="font-regular text-red-600 text-sm">
// //                 Please key in a valid phone number.
// //               </p>
// //             )}
// //           </div>
// //           <div className="">
// //             {inputFields.map(({ type, id, placeholder }, index) => (
// //               <input
// //                 key={index}
// //                 type={type}
// //                 id={id}
// //                 className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-black bg-white mb-4"
// //                 placeholder={placeholder}
// //               />
// //             ))}
// //           </div>
// //           <CountrySelector
// //             id={"countries"}
// //             open={isOpen}
// //             onToggle={() => setIsOpen(!isOpen)}
// //             onChange={(val) => setCountry(val)}
// //             // We use this type assertion because we are always sure this find will return a value but need to let TS know since it could technically return null
// //             selectedValue={
// //               COUNTRIES.find(
// //                 (option) => option.value === country
// //               ) as SelectMenuOption
// //             }
// //           />

// //           <button
// //             type="submit"
// //             className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
// //           >
// //             Save
// //           </button>
// //         </form>
// //       </main>
// //     </div>
// //   );
// // }

// // export default Info;