
interface EventButtonProps {
  name: string;
}

const FriendResult: React.FC<EventButtonProps> = ({ name }) => {
  return (
    <div>
      <button className="w-[200px] h-auto bg-zinc-900 hover:bg-zinc-800 text-white p-6 rounded-md drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="font-bold truncate">{name}</div>
      </button>
    </div>
  );
};

export default FriendResult;
