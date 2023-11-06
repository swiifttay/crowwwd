"use client"

import { addFriend } from "@/app/axios/apiService";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { useState } from "react";
interface EventButtonProps {
  username: string;
  firstName: string;
  lastName: string;
}

const PendingRequest: React.FC<EventButtonProps> = ({
  username,
  firstName,
  lastName,
}) => {

  const [isPendingFriend, setIsPendingFriend] = useState(false);

  // const [isFriendAdded, setIsFriendAdded] = useState(false);

  // const handleAddFriend = async () => {
  //   try {
  //     const response = await addFriend(username);
  //     if (response.status === 200) {
  //       setIsFriendAdded(true);
  //       console.log("Friend added successfully");
  //     } else {
  //       console.error("Error adding friend:", response.data);
  //     }
  //   } catch (error) {
  //     console.error("Error adding friend:", error);
  //   }
  // };

  return (
    <div>
      <button className="mt-1 flex flex-row justify-between align-middle w-[250px] h-auto bg-zinc-900 text-white py-2 px-3 rounded-md drop-shadow-[1px_1px_2px_rgba(113,113,113)] items-center">
        <div className="align-middle font-bold flex flex-row overflow-hidden items-center">
          {/* <AccountCircleIcon sx={{ fontSize: 32, color: "#e5e7eb" }} /> */}
          <img
            src="/images/DemiLovato.jpg"
            alt="User Picture"
            className="rounded-full"
            width={50}
            height={50}
          ></img>

          <div className="flex flex-col ml-1">
            <div className="">{firstName}</div>
            <div className="">{lastName}</div>
          </div>
          {/* <div className="mr-1 ml-1 mt-1">{firstName}</div>
          <div className="mt-1">{lastName}</div> */}
        </div>
        <div className="flex items-center">
          {isPendingFriend ? (
            <div className="text-theme-blue text-md mt-1">Pending</div>
          ) : (
            <button
              className="px-2 bg-theme-blue text-sm text-white py-2 rounded-lg hover:bg-theme-light-blue"
              onClick={() => setIsPendingFriend(true)}
            >
              Add Friend
            </button>
          )}
        </div>
      </button>
    </div>
  );
};

export default PendingRequest;
