"use client";

import { ReactNode } from "react";
import { GlobalStateProvider } from "./contexts/globalStateContext";
import { UserDetailsProvider } from "./contexts/UserDetailsContext";

export function Providers({ children }: { children: ReactNode }) {
  return (
    <GlobalStateProvider>
      <UserDetailsProvider>{children}</UserDetailsProvider>
    </GlobalStateProvider>
  );
}
