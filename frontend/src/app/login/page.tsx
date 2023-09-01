import Image from "next/image";
import Link from "next/link";
import DataForm from "../components/Login/DataForm";

function Login() {
  return (
    //<div className="flex flex-col items-center justify-center min-h-screen py-2">
      <main className="min-h-screen py-2 flex relative flex-col items-center justify-center w-full flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw]">
        <div className="text-4xl font-bold mb-4">Welcome to Crowd Sync</div>
        <p className="text-lg">Please log in to access your account.</p>

        <DataForm />

        {/* sign up button link */}
        <div className="mt-8 text-md flex space-x-2">
          <div className="text-theme-grey">New to CROWD SYNC?</div>
          <Link
            href="/register"
            className="text-theme-blue hover:text-theme-light-blue"
          >
            Sign up.
          </Link>
        </div>
      </main>
    //</div>
  );
}

export default Login;
