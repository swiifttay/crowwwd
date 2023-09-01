import Image from "next/image";
import Link from "next/link";
import LoginForm from "./LoginForm";

function Login() {
  return (
    <div className="items-center min-h-screen max-h-screen">
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
    </div>
  );
}

export default Login;

//lg:left-1/4 sm:w-[95vw] xs:w-[90vw]