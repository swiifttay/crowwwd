"use client";

import { createContext, useContext, useEffect, useState } from "react";
import { useLocalStorage } from "./hooks/useLocalStorage";

interface GlobalStateContextType {
  isOpen: boolean;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  isAuthenticated: boolean;
  setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>
  storeToken: (token: string) => void
  removeToken: () => void;
}

const GlobalStateContext = createContext<GlobalStateContextType>({} as GlobalStateContextType);

export const GlobalStateProvider = ({ children }: any) => {
  const [isOpen, setIsOpen] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useLocalStorage<string | null>("token", null);

  useEffect(() => {
    setIsAuthenticated(token != undefined ? true : false)
  },[])

  function storeToken(token: string) {
    setToken(token)
    //localStorage.setItem("token", token);
    setIsAuthenticated(true);
  }

  function removeToken() {
    localStorage.removeItem("token");
  }

  return (
    <GlobalStateContext.Provider value={{ isOpen, setIsOpen, isAuthenticated, setIsAuthenticated, storeToken, removeToken }}>
      {children}
    </GlobalStateContext.Provider>
  );
};

export const useGlobalState = (): GlobalStateContextType => {
  return useContext(GlobalStateContext);
  // if (!context) {
  //   throw new Error("useGlobalState must be used within a GlobalStateProvider");
  // }
};
