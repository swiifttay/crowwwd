"use client"

import { addFriend } from "@/app/axios/apiService";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { useState } from "react";
interface EventButtonProps {
  username: string;
  firstName: string;
  lastName: string;
}

const FriendResult: React.FC<EventButtonProps> = ({
  username,
  firstName,
  lastName,
}) => {
  const [isFriendAdded, setIsFriendAdded] = useState(false);

  const handleAddFriend = async () => {
    try {
      const response = await addFriend(username);
      if (response.status === 200) {
        setIsFriendAdded(true);
        console.log("Friend added successfully");
      } else {
        console.error("Error adding friend:", response.data);
      }
    } catch (error) {
      console.error("Error adding friend:", error);
    }
  };

  return (
    <div>
      <button className="mt-1 flex flex-row justify-between align-middle w-[250px] h-auto bg-zinc-900 text-white py-2 px-3 rounded-md drop-shadow-[1px_1px_2px_rgba(113,113,113)]">
        <div className="align-middle font-bold flex flex-row overflow-hidden">
          <AccountCircleIcon sx={{ fontSize: 32, color: "#e5e7eb" }} />
          <div className="mr-1 ml-1 mt-1">{firstName}</div>
          <div className="mt-1">{lastName}</div>
        </div>
        <div className="flex align-middle">
          {isFriendAdded ? (
            <div className="text-theme-blue text-sm mt-1">Already Added</div>
          ) : (
            <button
              className="px-2 bg-theme-blue text-sm text-white py-2 rounded-lg hover:bg-theme-light-blue"
              onClick={handleAddFriend}
            >
              Add Friend
            </button>
          )}
        </div>
      </button>
    </div>
  );
};

export default FriendResult;
