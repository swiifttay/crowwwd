import Image from "next/image";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

interface EventButtonProps {
  name: string;
}

const VerticalCard: React.FC<EventButtonProps> = ({ name }) => {
  return (
    <div>
      <button className="w-[200px] bg-zinc-900 hover:bg-zinc-800 text-white px-6 pt-4 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="mb-4">
          {/* <Image
            src={image}
            alt="Artist Picture"
            className="rounded-full object-cover max-h-[152px] max-w-[152px]"
            width={200}
            // maxwidth={200}
            height={200}
            // maxheight={200}
          /> */}
          <AccountCircleIcon sx={{ fontSize: 76, color: "#e5e7eb" }} />
        </div>

        <div className="font-bold mb-2 truncate">{name}</div>
      </button>
    </div>
  );
};

export default VerticalCard;
