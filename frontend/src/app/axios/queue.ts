import axios from "axios";
import { User } from "../contexts/UserDetailsContext";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

api.interceptors.request.use(async (config) => {
  try {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  } catch (error) {
    throw error;
  }
});

api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response?.data?.status === 500) {
      console.log("Handling 500 error");
      // localStorage.removeItem("token");
    }
    console.log(error.response);
    return error.response;
  }
);

export const getJoinQueue = async (eventId: string) => {
  const res = await api.get(`/queue/join/${eventId}`);
  return res.data;
};

export const getCheckQueue = async (eventId: string) => {
  const res = await api.get(`/queue/check/${eventId}`);
  console.log(res.status);
  return res.data;
};

export const getFrontQueue = async () => {
  const res = await api.get("/peopleInFront");
  return res.data;
};

export const getWholeQueue = async (eventId: string) => {
  const res = await api.get(`/queue/sizes/${eventId}`);
  return res.data;
};
