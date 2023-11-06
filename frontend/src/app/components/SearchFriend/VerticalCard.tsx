import Image from "next/image";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

interface EventButtonProps {
  image: string;
  firstName: string;
  lastName: string;
}

const VerticalCard: React.FC<EventButtonProps> = ({
  image,
  firstName,
  lastName,
}) => {
  return (
    <div>
      <button className="w-[200px] bg-zinc-900 hover:bg-zinc-800 text-white px-6 pt-4 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="mb-4">
          <Image
            src={image}
            alt="Artist Picture"
            className="rounded-full object-cover max-h-[152px] max-w-[152px]"
            width={200}
            // maxwidth={200}
            height={200}
            // maxheight={200}
          />
          {/* <AccountCircleIcon sx={{ fontSize: 76, color: "#e5e7eb" }} /> */}
        </div>

        <div className="flex flex-row justify-center mb-4">
          <div className="mr-1">{firstName}</div>
          <div className="">{lastName}</div>
        </div>
      </button>
    </div>
  );
};

export default VerticalCard;
