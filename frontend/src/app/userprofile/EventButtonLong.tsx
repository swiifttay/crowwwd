import Image from "next/image";
import React from "react";

interface EventButtonProps {
  image: string;
  title: string;
  artist: string;
  datetime: string;
  venue: string;
  setIsOpen: (isOpen: boolean) => void;
}

const EventButtonLong: React.FC<EventButtonProps> = ({
  image,
  title,
  artist,
  datetime,
  venue,
  setIsOpen,
}) => {
  const handleButtonClick = () => {
    setIsOpen(true);
  };

  return (
    <div>
      <button
        className="w-full bg-zinc-900 hover:bg-zinc-800 text-white pt-2 pb-1 px-5 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]"
        onClick={handleButtonClick}
      >
        <div className="flex">
          <div className="mr-6">
            <Image
              src={image}
              alt="Artist Picture"
              className="rounded-full"
              width={50}
              height={50}
            />
          </div>
          <div className="mt-0.5">
            <div className="font-bold text-left">{title}</div>
            <div className="text-sm text-left">{artist}</div>
          </div>
          <div className="ml-20 mt-1.5">
            <div className="text-sm text-left">{datetime}</div>
            <div className="text-sm text-left">{venue}</div>
          </div>
        </div>
      </button>
    </div>
  );
};

export default EventButtonLong;
