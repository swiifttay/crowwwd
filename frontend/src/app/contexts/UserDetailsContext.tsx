import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
} from "react";
import { useLocalStorage } from "../hooks/useLocalStorage";

type UserDetailsContext = {
  user: User | null;
  setUser: Dispatch<SetStateAction<User | null>>;
};

type UserDetailsProviderProps = {
  children: ReactNode;
};

export type User = {
  id: string;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  phoneNo: string;
  userCreationDate: string;
  countryOfResidence: string;
  city: string;
  state: string;
  address: string;
  postalCode: string;
  isPreferredMarketing: string;
  spotifyAccount: string;
};

const UserDetailsContext = createContext({} as UserDetailsContext);

export function useUserDetails() {
  return useContext(UserDetailsContext);
}

export function UserDetailsProvider({ children }: UserDetailsProviderProps) {
  const [user, setUser] = useLocalStorage<User | null>("user", null);

  return (
    <UserDetailsContext.Provider value={{ user, setUser }}>
      {children}
    </UserDetailsContext.Provider>
  );
}
