import React from "react";
import styles from "./Modal.module.css";
import { RiCloseLine } from "react-icons/ri";
import Dropdown from "./Dropdown";

const Modal = ({ setIsOpen }) => {
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
            <div className="text-base	font-extrabold text-md">Reputation Tour</div>
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
  );
};

export default Modal;
