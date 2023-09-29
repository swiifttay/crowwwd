"use client";
import Image from "next/image";
import { useRouter } from "next/navigation";

export default function Event() {
  const router = useRouter();
  const rulesHeader = "font-bold text-md ml-8 mb-2 mt-4";
  const rulesDetails = "list-disc ml-16";

  const handleBuyTickets = async () => {
    router.push("/order");
  };
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

        <div className="max-w-[1045px] mt-8">
          <div className="text-3xl font-bold mb-4">
            2023 KIM SEON HO ASIA TOUR in SINGAPORE [ONE, TWO, THREE. SMILE] [G]
          </div>

          <div className="flex justify-between">
            <p className="text-lg mb-10">KPOP | Concert</p>
            <button
              className="w-[150px] h-1/6 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded -mt-4"
              onClick={handleBuyTickets}
            >
              Buy Tickets
            </button>
          </div>

          <div className="flex gap-3 ml-10">
            <Image
              src="/images/icon-calendar.svg"
              alt="Calendar"
              width={20}
              height={20}
            />
            <div className="text-md mr-20 hover:text-theme-grey hover:underline cursor-pointer">
              Fri 15 Sep 2023, 7pm
            </div>
            <Image
              src="/images/icon-map-pin.svg"
              alt="Calendar"
              width={20}
              height={20}
            />
            <div className="text-md mr-20 hover:text-theme-grey hover:underline cursor-pointer">
              The Star Theatre, The Star Performing Arts Centre
            </div>
          </div>

          <p className="text-md mt-10 mb-10">
            KIM SEON HO is all set to take the stage in SINGAPORE with his
            highly anticipated fan meeting, bringing his undeniable talent,
            irresistible smile, and heartwarming presence to his dedicated
            Seonhohada. PULP Live World and Happee Hour present the 2023 KIM
            SEON HO ASIA TOUR in SINGAPORE on September 15, 2023, 7PM at THE
            STAR THEATRE.
          </p>

          <div className="font-semibold text-2xl mb-4">Price Details</div>
          <div className="grid text-md gap-2">
            <div>VIP: $328-$1228 </div>
            <div>CAT 1-3: $288-$348 </div>
            <div>CAT 4-6: $108-$248 </div>
            <div>CAT 7-9: $88-$248 </div>
            <div className="italic mt-6">
              *Ticketing price excludes booking charge
            </div>
            <div className="italic">
              *All tickets include a souvenir show card, official poster,
              special packet and good-bye session
            </div>
          </div>

          <button className="mt-10 w-[200px] h-1/6 border-2 hover:bg-neutral-600 text-white font-bold py-2 px-4 rounded">
            Seat Map
          </button>

          <div className="mt-12 text-xl font-bold mb-2">Admission Rules</div>
          <div className={rulesHeader}>Rating / Age Limit</div>
          <ul className={rulesDetails}>
            <li>Rating: General</li>
            <li>
              Children under 12 must be accompanied by an adult with a ticket
              for the same section
            </li>
            <li>Admission is subject to tickets produced at the entrance</li>
          </ul>
          <div className={rulesHeader}>Late Seating Advisory</div>
          <ul className={rulesDetails}>
            <li>
              Please be seated 15 minutes before the performance start time.
              There will be no admission into the venue once the performance has
              commenced. Admission may only be permitted during a suitable
              pause, depending on the nature of the performance
            </li>
          </ul>

          <div className="mt-12 text-xl font-bold mb-2">FAQ</div>
          <div className="flex gap-1">
            <div>Click</div>
            <div className="hover:underline text-theme-light-blue cursor-pointer">
              here
            </div>
            <div>to view the FAQ for this event.</div>
          </div>
        </div>
      </div>
    </div>
  );
}
