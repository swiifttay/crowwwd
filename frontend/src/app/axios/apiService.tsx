import axios from "axios";
import { Event } from "../explore/page";

const api = axios.create({
  //TODO: backend to provide
  baseURL: "http://localhost:8080/api/",
});

export const authenticate = async (credentials: {
  username: string;
  password: string;
}) => {
  try {
    const response = await api.post("/authenticate", { credentials });
    const { token } = response.data;
    localStorage.setItem("token", token);
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    } else {
      console.log(error);
    }
  }
};

export const register = async (registerDetails: {
  firstName: string;
  lastName: string;
  username: string;
  password: string;
}) => {
  try {
    const response = await api.post("/register", registerDetails);
    const { token } = response.data;
    localStorage.setItem("token", token);
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
  }
};

export const concertsList = async () => {
  const response = await api.get("/event/getAllEvents");
  return response.data.events;
}

export const getEvent = async (eventId: string) => {
  try {
    const response = await api.get(`/event/getEvent/6501c2167e60d210c8875fc6`);
    console.log(response.data.outputEvent);
    console.log("bye")
    return response.data.outputEvent;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
  }
}
