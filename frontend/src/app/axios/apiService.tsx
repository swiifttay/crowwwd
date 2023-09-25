import axios from "axios";
// import { Event } from "../explore/page";
// import { Concert } from "../explore/page";
// import { User } from "../userprofile/page";

const api = axios.create({
  //TODO: backend to provide
  baseURL: "http://localhost:8080/api/",
});

// api interceptor to place the jwt token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

//Login
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
    const response = await api.get(`/auth/findUsername/${username}`);
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
};

//User Profile Page

export const getUserProfile =async () => {
  try {
    const response = await api.get("/profile/findProfile");
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
  }
  
}

export const getFanRecords = async () => {
  try {
    const response = await api.get("/fanRecord/myFanRecords");
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);  
    }
  }
}

export const getArtistById = async (artistId: string) => {
  try {
    const response = await api.get(`artist/getArtist/${artistId}`);
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);  
    }
  }
}
