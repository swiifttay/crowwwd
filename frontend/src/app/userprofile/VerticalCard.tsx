import Image from "next/image";

interface EventButtonProps {
  image: string;
  name: string;
}

const VerticalCard: React.FC<EventButtonProps> = ({ image, name }) => {
  return (
    <div>
      <button className="w-[200px] bg-zinc-900 hover:bg-zinc-800 text-white px-6 pt-4 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="mb-4">
          <Image
            src={image}
            alt="Artist Picture"
            className="rounded-full"
            width={200}
            height={200}
          />
        </div>

        <div className="font-bold mb-2">{name}</div>
      </button>
    </div>
  );
};

export default VerticalCard;
