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
    const response = await api.post("/auth/authenticate", { credentials });
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
