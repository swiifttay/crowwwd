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
          <img
            src={image}
            alt="Artist Picture"
            className="rounded-full object-cover max-h-[152px] max-w-[152px]"
            width={200}
            // maxwidth={200}
            height={200}
            // maxheight={200}
          />
        </div>
        {/* <div className="mb-4 ">
          <img
            src={image}
            alt="Artist Picture"
            className="rounded-full object-cover"
            style={{ width: '200px', height: '200px'}}
          />
        </div> */}
 

        <div className="font-bold mb-2 truncate">{name}</div>
      </button>
    </div>
  );
};

export default VerticalCard;
