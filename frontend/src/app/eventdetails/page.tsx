import Image from "next/image";

export default function Event() {
  return (
    <div className="flex flex-col justify-center items-center">
      <div className="w-auto mt-32">
        <div className="">
          <Image
            src="/images/EventPoster.jpg"
            alt="Event Poster"
            className="rounded-3xl"
            width={1045}
            height={487}
          />
        </div>

        <div className="max-w-[1045px] mt-4">
          <div className="text-3xl font-bold mb-4">
            2023 KIM SEON HO ASIA TOUR in SINGAPORE [ONE, TWO, THREE. SMILE] [G]
          </div>
          <p className="text-lg">KPOP | Concert</p>
        </div>
      </div>
    </div>
  );
}
