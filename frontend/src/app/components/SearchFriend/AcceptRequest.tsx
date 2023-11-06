"use client";

import Image from "next/image";
import React, { useState } from "react";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

interface EventButtonProps {
  image: string;
  firstName: string;
  lastName: string;
  onAcceptRequest: () => void;
}

const AcceptRequest: React.FC<EventButtonProps> = ({
  image,
  firstName,
  lastName,
  onAcceptRequest,
}) => {
  // const [isAcceptedFriend, setIsAcceptedFriend] = useState(false);

  const handleAcceptRequest = () => {
    // setIsAcceptedFriend(true);
    onAcceptRequest();
  };

  return (
    <div>
      <div className="mb-6 h-auto w-full bg-zinc-900  text-white pt-2 pb-1 px-5 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)] flex flex-row items-center">
        <div className="flex items-center flex-row justify-between w-full">
          <div className="mr-2">
            <Image
              src={image}
              alt="User Picture"
              className="rounded-full"
              width={50}
              height={50}
            />
            {/* <AccountCircleIcon sx={{ fontSize: 32, color: "#e5e7eb" }} /> */}
          </div>

          <div className="flex flex-row">
            <div className="mr-1 ">{firstName}</div>
            <div className="">{lastName}</div>
          </div>

          <div className="flex ml-auto justify-items-end">
            {/* {isAcceptedFriend ? (
              <div className="text-theme-blue text-md">Accepted</div>
            ) : ( */}
              <button
                className=" px-2 bg-theme-blue text-sm text-white py-2 rounded-lg hover:bg-theme-light-blue"
                onClick={handleAcceptRequest}
              >
                Accept Request
              </button>
            {/* )} */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AcceptRequest;
