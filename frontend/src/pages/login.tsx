import Image from 'next/image';
import RootLayout from '@/app/layout';
import backgroundImg from '../../public/images/Singer.jpg';
import Link from 'next/link';

function Login() {
  return (
    <RootLayout>
      <div className="flex flex-col items-center justify-center min-h-screen py-2"
        style={{
          backgroundImage: `url(${backgroundImg})`, //idk how to make the background img show up...
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <main className="flex relative flex-col items-center justify-center w-full flex-1 px-20 text-center lg:left-1/4 sm:w-[95vw] xs:w-[90vw]">
          <div className="text-4xl font-bold mb-4">Welcome to Crowwwd Sync</div>
          <p className="text-lg">Please log in to access your account.</p>

          <form className="mt-8 w-full max-w-sm">
            <div className="mb-4">
              <input
                type="text"
                id="username"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-md text-white bg-midnight"
                placeholder="Enter your username/email"
              />
            </div>
            <div className="mb-4">
              <input
                type="password"
                id="password"
                className="mt-1 px-3 py-2 w-full border border-zinc-500 rounded-md text-white bg-midnight"
                placeholder="Enter your password"
              />
            </div>
            <button
              type="submit"
              className="mt-6 w-full bg-theme-blue text-white py-2 rounded-md hover:bg-theme-light-blue"
            >
              Log In
            </button>
          </form>
          <div className="mt-8 text-md flex space-x-2">
            <div className="text-theme-grey">New to CROWD SYNC?</div>
            <Link href="/signup" className="text-theme-blue hover:text-theme-light-blue">Sign up.</Link>
          </div>
        </main>
      </div>
    </RootLayout>
  );
}

export default Login;
