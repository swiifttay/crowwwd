import Image from "next/image";
import RootLayout from "@/app/layout";
import backgroundImg from "../../public/images/Singer.jpg";
import Link from "next/link";

function Signup() {
  return (
    <RootLayout>
      <div
        className="flex flex-col items-center justify-center min-h-screen py-2"
        style={{
          backgroundImage: `url(${backgroundImg})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        <main className="flex relative flex-col items-center justify-center w-full flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw]">
          <div className="text-4xl font-bold mb-4">Join Crowd Sync</div>
          <p className="text-lg">Create your account to get started.</p>

          <form className="mt-8 w-full max-w-sm">
            <div className="flex space-x-2">
              <div className="flex-1 mb-4">
                <input
                  type="text"
                  id="firstName"
                  className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
                  placeholder="First Name"
                />
              </div>
              <div className="flex-1 mb-4">
                <input
                  type="text"
                  id="lastName"
                  className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
                  placeholder="Last Name"
                />
              </div>
            </div>
            <div className="mb-4">
              <input
                type="text"
                id="username"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
                placeholder="Enter your username/email"
              />
            </div>
            <div className="mb-4">
              <input
                type="password"
                id="password"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-lg text-white bg-theme-midnight"
                placeholder="Create a password"
              />
            </div>
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
      </div>
    </RootLayout>
  );
}

export default Signup;
