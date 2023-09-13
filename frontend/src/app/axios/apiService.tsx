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
  const response : Event[] = await api.get("/v1/getAllEvents");
  return response;
}
