import axios from "axios";
import { Concert } from "../explore/page";

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
    // if (axios.isAxiosError(error)) {
    //   console.log("error status", error.response?.status);
    //   // console.log(error);
    //   if (error.response?.status === 400) {
    //     console.log("bad error");
    //   } else if (error.response?.status === 401) {
    //     console.log("invalid cred error");
    //     // return;
    //   } else {
    //     console.log("Other error status");
    //   }
    // }
  }
};

export const register = async (registerDetails: {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;

  nationality: string;
  countryOfResidence: string;
  countryCode: string;
  gender: string;
  dateOfBirth: string;
  address: string;
  postalCode: string;
  phoneNo: string;
}) => {
  try {
    const response = await api.post("/auth/register", registerDetails);
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
  const response: Concert[] = await api.get("/event/getAllEvents");
  return response;
};
