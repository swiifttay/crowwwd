// "use client";

// import React, { useState } from "react";
// import { register } from "../axios/apiService";
// import DataEntry from "../components/Login/DataEntry";
// import Link from "next/link";

// export default function RegisterForm({ registerDetails, setRegisterDetails }) {
//   // const [registerDetails, setRegisterDetails] = useState({
//   //   firstName: "",
//   //   lastName: "",
//   //   username: "",
//   //   password: "",
//   //   email: "",
//   //   confirmPassword: "",
//   //   nationality: "a",
//   //   countryOfResidence: "a",
//   //   countryCode: "a",
//   //   gender: "a",
//   //   dateOfBirth: "a",
//   //   address: "a",
//   //   postalCode: "a",
//   //   phoneNo: "a",
//   // });

//   const [msg, setMsg] = useState("");

//   const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
//     e.preventDefault();

//     // console.log(registerDetails);

//     if (
//       !isValid(
//         registerDetails.email,
//         registerDetails.password,
//         registerDetails.confirmPassword
//       )
//     ) {
//       if (registerDetails.password.length < 8) {
//         setMsg("Password should be at least 8 characters");
//       } else if (
//         !registerDetails.email.match(
//           /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
//         )
//       ) {
//         setMsg("Email is invalid");
//       } else if (registerDetails.password !== registerDetails.confirmPassword) {
//         setMsg("Passwords are not consistent");
//       }
//       return;
//     }

//     // register(registerDetails);
//   };

//   const updateTextHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
//     setRegisterDetails((prevState) => {
//       return { ...prevState, [e.target.id]: e.target.value };
//     });
//   };

//   const isValid = (email : string,
//      password: string,
//      confirmPassword: string) => {
//     if (
//       !email.match(
//         /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
//       )
//     )
//       return false;
//     if (password !== confirmPassword) return false;
//     if (password.length < 8) return false;
//     return true;
//   };

//   return (
//     <form className="mt-8 w-full max-w-sm" onSubmit={submitHandler}>
//       <div className="flex space-x-2">
//         <DataEntry
//           type="text"
//           id="firstName"
//           placeholder="First Name"
//           onTextChange={updateTextHandler}
//           // value={registerDetails.firstName}
//         />
//         <DataEntry
//           type="text"
//           id="lastName"
//           placeholder="Last Name"
//           onTextChange={updateTextHandler}
//           // value={registerDetails.lastName}
//         />
//       </div>
//       <DataEntry
//         type="text"
//         id="username"
//         placeholder="Username"
//         onTextChange={updateTextHandler}
//         // value={registerDetails.username}
//       />
//       <DataEntry
//         type="text"
//         id="email"
//         placeholder="Email"
//         onTextChange={updateTextHandler}
//         // value={registerDetails.email}
//       />
//       <DataEntry
//         type="password"
//         id="password"
//         placeholder="Password"
//         onTextChange={updateTextHandler}
//         // value={registerDetails.password}
//       />
//       <DataEntry
//         type="password"
//         id="confirmPassword"
//         placeholder="Confirm Password"
//         onTextChange={updateTextHandler}
//         // value={registerDetails.confirmPassword}
//       />

//       <Link href="/info">
//         <button
//           type="submit"
//           className="mt-4 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
//         >
//           Sign Up
//         </button>
//       </Link>
//       <div className="text-red-500 mt-2">{msg}</div>
//     </form>
//   );
// }
