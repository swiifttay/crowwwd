import axios from "axios";
// import { Event } from "../explore/page";
// import { Concert } from "../explore/page";
// import { User } from "../userprofile/page";

const api = axios.create({
  //TODO: backend to provide
  baseURL: "http://localhost:8080/api/",
});

// api interceptor to place the jwt token
// api.interceptors.request.use(
//   (config) => {
//     const token = localStorage.getItem('token');
//     if (token) {
//       config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
//   },
//   (error) => {
//     console.log({error});
//     if (error.response?.data?.status === 500) {
//       console.log("here");
//       localStorage.removeItem('token');
//     }
//     return Promise.reject(error)
//   }
// );
api.interceptors.request.use(async (config) => {
  try {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  } catch (error) {
    // console.error('Error setting authorization header:', error);
    throw error;
  }
});

api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    // console.error('Error:', {error});

    if (error.response?.data?.status === 500) {
      console.log("Handling 500 error");
      localStorage.removeItem("token");
      // return a false value that shows that the token was invalid
      // return false;
    }
    console.log(error.toJSON());
    return error.toJSON();
  },
);


//Login
export const authenticate = async (credentials: {
  username: string;
  password: string;
}) => {
  // get the response
  const response = await api.post("/auth/authenticate", credentials);

  // check if valid response
  if (response.status === 200) {
    const { token } = response.data;
    localStorage.setItem("token", token);
  }

  return response;
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
  // create an account
  const response = await api.post("/auth/register", registerDetails);

  return response;
};

export const usernameCheck = async (username: string) => {
  try {
    const response = await api.get(`/auth/findUsername/${username}`);
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
    return Promise.reject(error);
  }
};

export const concertsList = async () => {
  const response = await api.get("/event/getAllEvents");

  return response;
};

//User Profile Page

export const getUserProfile = async () => {
  try {
    const response = await api.get("/profile/findProfile");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const getFanRecords = async () => {
  try {
    const response = await api.get("/fanRecord/myFanRecords");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const getArtistById = async (artistId: string) => {
  try {
    const response = await api.get(`artist/getArtist/${artistId}`);
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const getSpotifyLogin = async () => {
  try {
    const response = await api.get("/spotify/login");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const getSpotifyToken = async() => {
  try {
    const response = await api.get("/spotify/getSpotifyToken");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
}

export const updateFanRecords = async () => {
  try {
    const response = await api.post("/spotify/updateMyAccountFavouriteArtists");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const confirmSeats = async (SeatsConfirmRequest: 
  {eventId: string;
  category: string;
  allocatedSeats: string[];
  userIdsAttending: string[];
  noOfSurpriseTickets: number}) => {
  try {
    const response = await api.put("", SeatsConfirmRequest);
    return response;
  } catch(error){
    return Promise.reject(error);
  }
  }

  export const cancelSeats = async (SeatsCancelRequest: {
    eventId: string;
    category: string;
    allocatedSeats: string[] }) => {
    try {
      const response = await api.delete("",SeatsCancelRequest)
      return response;
    } catch(error){
      return Promise.reject(error);
    }
  }
