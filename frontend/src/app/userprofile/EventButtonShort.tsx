import Image from "next/image";

interface EventButtonProps {
  image: string;
  title: string;
  artist: string;
}

const EventButtonShort: React.FC<EventButtonProps> = ({
  image,
  title,
  artist,
}) => {
  return (
    <div>
      <button className="w-full bg-zinc-900 hover:bg-zinc-800 text-white pt-1.5 pb-1 px-3 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="flex">
          <div className="mr-4">
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
        </div>
      </button>
    </div>
  );
};

export default EventButtonShort;
