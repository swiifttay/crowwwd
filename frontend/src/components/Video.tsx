import React from 'react'

export default function Video() {
  return (
    <div className="h-screen flex justify-center">
      <div className="absolute inset-0 z-0">
        <video
          autoPlay
          muted
          loop
          className="w-full h-full object-cover opacity-60"
        >
          <source src="/videos/Landingvid.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
    </div>
    </div>
  )
}