import React from 'react'
import Link from 'next/link'

const page = () => {
  return (
      <div className="min-h-screen w-full px- 5 mb-10 rounded-3xl bg-failedpayment bg-cover bg-no-repeat bg-center flex flex-col justify-center items-center shadow-lg shadow-slate-950">
        <h1 className=" m-2 text-5xl font-semibold text-center">
          Sorry the payment connection has timed out
        </h1>
          <Link href="/explore" className='text-xl inline-block px-4 py-2 mt-4 text-white border border-white rounded hover:scale-110 hover:text-white hover:border-white transition duration-300'>Try Again</Link>
      </div>
  )
}

export default page