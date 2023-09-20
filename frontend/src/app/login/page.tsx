import Image from "next/image";
import Link from "next/link";
import LoginForm from "./LoginForm";
import AccountSwitch from "../components/Login/AccountSwitch";

export default function Login() {
  return (
    <div className="items-center max-h-screen">
      <Image
        src="/images/login-bg.jpg"
        alt="login-bg"
        layout="fill"
        objectFit="cover"
      />

      <main className="absolute w-1/2 right-0 py-2 h-full flex flex-col items-center justify-center flex-1 px-20 text-center">
        <div className="text-4xl font-bold mb-4">Welcome to Crowd Sync</div>
        <p className="text-lg">Please log in to access your account.</p>

        <LoginForm />
        <AccountSwitch
          message="New to CROWD SYNC?"
          link="/register"
          prompt="Sign up."
        />
      </main>
    </div>
  );
}

//lg:left-1/4 sm:w-[95vw] xs:w-[90vw]
