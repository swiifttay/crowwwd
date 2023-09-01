import Image from 'next/image';

export default function Login() {
  return (

    <div className="flex flex-col items-center justify-center min-h-screen py-2 bg-black">
      <main className="flex flex-col items-center justify-center w-full flex-1 px-20 text-center">
        <div className="">Sign in</div>
      </main>
    </div>

    // <div className="h-screen flex justify-center">
    //   <div className="absolute inset-0"
    //     // style={{
    //     //   backgroundImage: "url('/images/Singer.jpg')",
    //     //   backgroundSize: 'cover',
    //     //   backgroundPosition: 'center',
    //     // }}
    //   >
    //     {/* <Image
    //       src="/images/Singer.jpg"
    //       height={1078}
    //       width={2148}
    //       alt="Your Name"
    //     /> */}

    //     {/* Other login content */}
    //   </div>
    // </div>
  );
}
