import AccountCircleIcon from "@mui/icons-material/AccountCircle";
interface EventButtonProps {
  firstName: string;
  lastName: string;
}

const FriendResult: React.FC<EventButtonProps> = ({ firstName, lastName }) => {
  return (
    <div>
      <button className="flex flex-row justify-between align-middle w-[250px] h-auto bg-zinc-900 text-white py-2 px-3 rounded-md drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="align-middle font-bold flex flex-row overflow-hidden">
          <AccountCircleIcon sx={{ fontSize: 32, color: "#e5e7eb" }} />
          <div className="mr-1 ml-1 mt-1">{firstName}</div>
          <div className="mt-1">{lastName}</div>
        </div>
        <div className="flex align-middle">
          <button
            // onClick={() => handleAddFriend(result.id)}
            className="px-2 bg-theme-blue text-sm text-white py-2 rounded-lg hover:bg-theme-light-blue"
          >
            Add Friend
          </button>
        </div>
      </button>
    </div>
  );
};

export default FriendResult;
