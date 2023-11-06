import React from "react";
import { RiCloseLine } from "react-icons/ri";
import Dropdown from "./Dropdown";
import SingleSelect from "./FriendDropdown";
import NumTicket from "./NumTicketsDropdown";
import styles from "./Modal.module.css";

interface ModalProps {
  setIsOpen: (isOpen: boolean) => void;
  title: string;
  artist: string;
  datetime: string;
  venue: string;
}

const Modal: React.FC<ModalProps> = ({
  setIsOpen,
  title,
  artist,
  datetime,
  venue,
}) => {
  return (
    <>
      <div className={styles.darkBG} onClick={() => setIsOpen(false)} />
      <div className={styles.centered}>
        <div className={styles.modal}>
          <div className={styles.modalHeader}>
            <h5 className={styles.heading}>Transfer Tickets to Friends</h5>
          </div>
          <button className={styles.closeBtn} onClick={() => setIsOpen(false)}>
            <RiCloseLine style={{ marginBottom: "-3px" }} />
          </button>
          <div className={styles.modalContent}>
            <div className="text-base	font-extrabold text-md">{title}</div>
            <div className="text-base	font-extrabold mb-2">{artist}</div>
            <div>{datetime}</div>
            <div>{venue}</div>
          </div>
          <div className={styles.modalActions}>
            <div className={styles.actionsContainer}>
              <SingleSelect />
              <NumTicket />
            </div>
          </div>
          <div className={styles.modalActions}>
            <div className={styles.actionsContainer}>
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
  );
};

export default Modal;
