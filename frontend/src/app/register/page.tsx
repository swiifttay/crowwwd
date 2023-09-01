import Image from "next/image";
import Link from "next/link";
import RegisterForm from "./RegisterForm";
import AccountSwitch from "../components/Login/AccountSwitch";

export default function Register() {
  return (
    <div className="flex flex-col items-center justify-center h-full py-2">
      <Image
        src="/images/login-bg.jpg"
        alt="login-bg"
        layout="fill"
        objectFit="cover"
      />

      <main className="absolute w-1/2 right-0 py-2 h-full flex flex-col items-center justify-center flex-1 px-20 text-center">
        <div className="text-4xl font-bold mb-4">Join Crowd Sync</div>
        <p className="text-lg">Create your account to get started.</p>

        <RegisterForm />
        <AccountSwitch message="Already have an account?" link="/login" prompt="Log in." />
        
      </main>
    </div>
  );
}

// Original tailwindclass for main
// flex relative flex-col items-center justify-center w-3/12 flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw] min-h-screen py-2
