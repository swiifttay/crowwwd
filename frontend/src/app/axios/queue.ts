import axios from "axios";
import { User } from "../contexts/UserDetailsContext";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export const putJoinQueue = async (eventId: string) => {
  const res = await api.put(`/queue/join/${eventId}`);
  return res.data;
};

export const getCheckQueue = async (user: User, eventId: string) => {
  const res = await api.put(`/queue/check/${eventId}`)
};

export const getFrontQueue = async () => {
  const res = await api.get("/peopleInFront");
  return res.data;
};

export const getWholeQueue = async (eventId:string) => {
  const res = await api.get(`/queue/sizes/${eventId}`);
  return res.data;
};
