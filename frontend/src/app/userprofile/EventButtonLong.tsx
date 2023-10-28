import Image from "next/image";
import React from "react";
import { RiCloseLine } from "react-icons/ri";
import Dropdown from "../components/UserProfile/Dropdown";
import styles from "../components/UserProfile/Modal.module.css";

interface EventButtonProps {
  image: string;
  title: string;
  artist: string;
  datetime: string;
  venue: string;
  setIsOpen: boolean;
}

const EventButtonLong: React.FC<EventButtonProps> = ({
  image,
  title,
  artist,
  datetime,
  venue,
  setIsOpen = false,
}) => {
  return (
    <div>
      <button
        className="w-full bg-zinc-900 hover:bg-zinc-800 text-white pt-2 pb-1 px-5 rounded-lg drop-shadow-[1px_1px_2px_rgba(113,113,113)]"
        onClick={() => setIsOpen(true)}
      >
        <div className="flex">
          <div className="mr-6">
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
          <div className="ml-20 mt-1.5">
            <div className="text-sm text-left">{datetime}</div>
            <div className="text-sm text-left">{venue}</div>
          </div>
        </div>
      </button>

      {setIsOpen ? (
        <>
          {/* <div className={styles.darkBG} onClick={() => setIsOpen(true)} /> */}
          <div className={styles.centered}>
            <div className={styles.modal}>
              <div className={styles.modalHeader}>
                <h5 className={styles.heading}>Transfer Tickets to Friends</h5>
              </div>
              <button
                className={styles.closeBtn}
                onClick={() => setIsOpen(false)}
              >
                <RiCloseLine style={{ marginBottom: "-3px" }} />
              </button>
              <div className={styles.modalContent}>
                <div className="text-base	font-extrabold text-md">
                  Reputation Tour
                </div>
                <div className="text-base	font-extrabold mb-2">Taylor Swift</div>
                <div>Fri 15 Sep 2023, 7pm</div>
                <div>The Star Theatre, The Star Performing Arts Centre</div>
              </div>
              <div className={styles.modalActions}>
                <div className={styles.actionsContainer}>
                  <Dropdown />
                  <button
                    className={styles.confirmBtn}
                    onClick={() => setIsOpen(false)}
                  >
                    Confirm
                  </button>
                </div>
              </div>
            </div>
          </div>
        </>
      ) : null}
    </div>
  );
};

export default EventButtonLong;
