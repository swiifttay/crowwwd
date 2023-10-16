import Link from 'next/link'

export default function NotFound() {
  return (
    <main className="flex flex-col justify-center items-center relative w-full h-[30vw] px-8">
      <div className="text-center align-center mt-4 mb-4 text-2xl font-bold">
        Oops! Page is not found.
      </div>
      <div className="flex text-xl font-bold gap-1">
        <div>Return to</div>
        <div className="text-theme-blue hover:text-theme-light-blue">
          <a href="/">HOME</a>
        </div>
      </div>
    </main>
  )
}