"use client"

import { type } from "os";
import { useEffect, useState, Dispatch, SetStateAction } from "react";

export function useLocalStorage<T>(key: string, initialValue: T): [T, Dispatch<SetStateAction<T>>] {
  const [value, setValue] = useState<T>(() => {
    if (typeof window !== "undefined") {
      const storedValue = localStorage.getItem(key);
      return storedValue !== null ? storedValue as T : initialValue;
    }
    return initialValue;
  });

  useEffect(() => {
    if (value !== null)
     localStorage.setItem(key, typeof value === "string" ? value : JSON.stringify(value));
  }, [key, value]);

  return [value, setValue];
};