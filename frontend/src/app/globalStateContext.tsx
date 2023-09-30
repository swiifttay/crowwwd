"use client";

import { createContext, useContext, useState } from "react";

interface GlobalStateContextType {
  isOpen: boolean;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

const GlobalStateContext = createContext<GlobalStateContextType | null>(null);

export const GlobalStateProvider = ({ children }: any) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <GlobalStateContext.Provider value={{ isOpen, setIsOpen }}>
      {children}
    </GlobalStateContext.Provider>
  );
};

export const useGlobalState = (): GlobalStateContextType => {
  const context = useContext(GlobalStateContext);
  if (!context) {
    throw new Error("useGlobalState must be used within a GlobalStateProvider");
  }
  return context;
};
