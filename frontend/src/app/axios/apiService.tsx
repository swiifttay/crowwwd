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
    const response = await api.post("/auth/authenticate", credentials);
    const { token } = response.data;
    localStorage.setItem("token", token);
    return true;
  } catch (error) {
    return false;
  }
};

export const registerAccount = async (registerDetails: {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  countryOfResidence: string;
  // dateOfBirth: string;
  city: string;
  state: string;
  address: string;
  postalCode: string;
  phoneNo: string;
}) => {
  try {
    const response = await api.post("/auth/register", registerDetails);
    // const { token } = response.data;
    // localStorage.setItem("token", token);
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
  }
};

export const usernameCheck = async (username: string) => {
  try {
    const response = await api.get("/auth/findUsername/${username}");
    return false;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      if (error.response?.data.message.contains("exists")) {
        return true;
      }
    }
  }
}

export const concertsList = async () => {
  const response = await api.get("/event/getAllEvents");
  // console.log(response.data.events);
  // console.log(response.data)
  return response.data.events;
}
