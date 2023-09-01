import Image from "next/image";
import Link from "next/link";
import DataEntry from "../components/Login/DataEntry";

export default function Register() {
  return (
    //<div className="flex flex-col items-center justify-center min-h-screen py-2">
      <main className="flex relative flex-col items-center justify-center w-full flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw] min-h-screen py-2">
        <div className="text-4xl font-bold mb-4">Join Crowd Sync</div>
        <p className="text-lg">Create your account to get started.</p>

        <form className="mt-8 w-full max-w-sm">
          <div className="flex space-x-2">
            <DataEntry type="text" id="firstName" placeholder="First Name" />
            <DataEntry type="text" id="lastName" placeholder="Last Name" />
          </div>
          <DataEntry type="text" id="username" placeholder="Username/email" />
          <DataEntry type="password" id="password" placeholder="Password" />

          <button
            type="submit"
            className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
          >
            Sign Up
          </button>
        </form>
        <div className="mt-8 text-md flex space-x-2">
          <div className="text-theme-grey">Already have an account?</div>
          <Link
            href="/login"
            className="text-theme-blue hover:text-theme-light-blue"
          >
            Log in.
          </Link>
        </div>
      </main>
    //</div>
  );
}
