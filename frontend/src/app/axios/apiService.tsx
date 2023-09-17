import axios from "axios";
import { Concert } from "../explore/page";
import { User } from "../userprofile/page";

const api = axios.create({
  //TODO: backend to provide
  baseURL: "http://localhost:8080/api/",
});

//Login
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

//Register
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

//Explore Page
export const concertsList = async () => {
  const response: Concert[] = await api.get("/v1/getAllEvents");
  return response;
};

//User Profile Page

export const getUserProfile =async () => {
  try {
    const response: User = await api.get("/profile/findProfile");
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
  }
  
}
