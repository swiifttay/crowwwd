import axios from "axios";
import { SeatsConfirmRequest } from "../components/Processing/ProcessingPage";
// import { Event } from "../explore/page";
// import { Concert } from "../explore/page";
// import { User } from "../userprofile/page";

const api = axios.create({
  //TODO: backend to provide
  baseURL: `http://localhost:8080/api`,
});

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
    if (error.response?.data?.status === 500) {
      console.log("Handling 500 error");
      // localStorage.removeItem("token");
      // return a false value that shows that the token was invalid
      // return false;
    }
    console.log(error.response);
    return error.response;
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

export const getAllEvents = async () => {
  const response = await api.get("/event/exploreEvent/all");
  console.log(response);

  return response.data;
};

export const getEvent = async (eventId: string) => {
  try {
    const response = await api.get(`/event/fullEvent/${eventId}`);
    console.log(response);
    if (response.request?.status === 200) {
      return response.data;
      // will come here if it was from a token error
    } else {
      window.location.reload();
    }

    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.log(error.status);
      console.error(error.response);
    }
  }
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

export const searchProfile = async (username: string) => {
  try {
    const response = await api.get(`/profile/searchProfile/${username}`);
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const addFriend = async (friendId: string) => {
  try {
    const response = await api.post(`/friend/${friendId}`);
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

export const getSpotifyToken = async () => {
  try {
    const response = await api.get("/spotify/getSpotifyToken");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const updateFanRecords = async () => {
  try {
    const response = await api.post("/spotify/updateMyAccountFavouriteArtists");
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

export const fetchOrderByOrderId = async (orderId: string) => {
  try {
    const response = await api.get(`/order/${orderId}`);
    return response;
  } catch(error){
    return Promise.reject(error);
  }

}

export const updateOrder = async (orderId: string, paymentId: string|undefined) => {
  try {
    const response = await api.put(`/order/${orderId}/payment/${paymentId}`);
    return response;
  } catch(error){
    return Promise.reject(error);
  }
}

export const fetchOrderByPaymentId = async (paymentId: string) => {
  try {
    const response = await api.get(`/orderPayment/${paymentId}`);
    return response;
  } catch(error){
    return Promise.reject(error);
  }
}


export const confirmSeats = async ({orderId, paymentId, userIdsAttending, noOfSurpriseTickets}: SeatsConfirmRequest) => {
  try {
    const response = await api.put("/seat", {orderId, paymentId, userIdsAttending, noOfSurpriseTickets});
    return response;
  } catch(error){
    return Promise.reject(error);
  }
}

  export const deleteOrderByOrderId = async (orderId:string|undefined) => {
    try {
      const response = await api.delete(`/order/${orderId}`)
      return response;
    } catch(error){
      return Promise.reject(error);
    }
  }

  export const approvedFriend = async () => {
    try {
      const response = await api.get("/approvedFriend")
      return response;
    } catch(error){
      return Promise.reject(error);
    }
  }

export const getCategoryPrice = async (eventId: string) => {
  const res = await api.get(`/event/${eventId}/event-seating-details`);
  return res.data;
};

