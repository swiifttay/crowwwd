"use client";

import { ReactNode } from "react";
import { GlobalStateProvider } from "./globalStateContext";

export function Providers({children}: {children: ReactNode}) {
    return (
        <GlobalStateProvider>
            {children}
        </GlobalStateProvider>
    )
}