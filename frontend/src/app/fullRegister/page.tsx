"use client";

import React, { useState } from "react";
import Image from "next/image";
import AccountSwitch from "../components/Login/AccountSwitch";
// import { ActiveStepFormComponent } from "./ActiveStepFormComponent";

import { useFormState, FormProvider } from "../components/Register/FormContext";
import { SimpleDetailForm } from "../components/Register/SimpleDetailForm";
import { ComplexDetailForm } from "../components/Register/ComplexDetailForm";


function ActiveStepFormComponent() {
  const { step } = useFormState();
  switch (step) {
    case 0:
      return <SimpleDetailForm />;
    case 1:
      return <ComplexDetailForm />;
    default:
      return null;
  }
}

export default function Home() {
  const [step, useStep] = useState(0);
  return (

    <div className="flex items-center justify-center w-full h-screen overflow-hidden">
      <Image
        src="/images/login-bg.jpg"
        alt="login-bg"
        layout="fill"
        objectFit="cover"
      />

      {/* <main className="absolute w-1/2 right-0 py-2 max-h-screen flex flex-col items-center justify-center flex-1 px-20 text-center"> */}
      <main className="mt-8 flex relative flex-col items-center justify-center w-full flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw]  py-2">
        <div className="text-4xl font-bold mb-4">Join Crowd Sync</div>
        <p className="text-lg">Create your account to get started.</p>

        <FormProvider>
          <ActiveStepFormComponent />
        </FormProvider>

        <AccountSwitch
          message="Already have an account?"
          link="/login"
          prompt="Log in."
        />
      </main>
    </div>
  );
}