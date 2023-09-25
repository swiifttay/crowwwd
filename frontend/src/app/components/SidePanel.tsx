'use client'

import React, { useState } from 'react'
import Link from 'next/link'
import { XMarkIcon } from '@heroicons/react/20/solid'
import { useGlobalState } from '../globalStateContext'

const SidePanel = () => {
  const {isOpen, setIsOpen}: any = useGlobalState();
  const [expand, setExpand] = useState(false);

  return (
    <div className={`absolute flex flex-col space-y-10 p-10 items-start text-white bg-theme-midnight bg-opacity-90 h-full z-50 w-1/2 transform transition-transform duration-300 ease-in-out ${isOpen ? 'translate-x-0' : 'hidden'}`}
      onClick={()=> setIsOpen(false)}>
        <Link href='/explore' className='hover:scale-110'>Explore</Link>
        <Link href='' className='hover:scale-110'>About Us</Link>
 
        <Link href='/login' className='hover:scale-110'>Login</Link>
        <div className="flex space-x-2 items-center">
          <a
            href="https://open.spotify.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer hover:scale-110"
          >
            <img src="/images/icons8-spotify.svg" alt="spotify image" width="30" height="30" />
          </a>
          <a
            href="https://twitter.com/home"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer hover:scale-110"
          >
            <img src="/images/icons8-twitterx.svg" alt="twitter x image" width="30" height="30" />
          </a>
          <a
            href="https://www.instagram.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="hover:cursor-pointer hover:scale-110"
          >
            <img src="/images/icons8-instagram.svg" alt="instagram image" width="30" height="30" />
          </a>
        </div>
        <XMarkIcon className='h-10 cursor-pointer hover:scale-125'/>
        
    </div>
  )
}

export default SidePanel