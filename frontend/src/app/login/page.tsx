import Image from "next/image";
import Link from "next/link";
import LoginForm from "./LoginForm";
import AccountSwitch from "../components/Login/AccountSwitch";

export default function Login() {
  return (
    <div className="flex items-center justify-center w-full h-screen bg-login bg-cover bg-center">
      <main className="mt-8 flex relative flex-col items-center justify-center w-full flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw]  py-2-center">
        <div className="text-4xl font-bold mb-4">Welcome to Crowd Sync</div>
        <p className="text-lg">Please log in to access your account.</p>

        <LoginForm />
        <AccountSwitch
          message="New to CROWD SYNC?"
          link="/fullRegister"
          prompt="Sign up."
        />
      </main>
    </div>
  );
}

//lg:left-1/4 sm:w-[95vw] xs:w-[90vw]
