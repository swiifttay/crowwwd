import axios from "axios";

const api = axios.create({
  //TODO: backend to provide
  baseURL: "http://localhost:8080/api",
});

export const getSeatsByEventAndCategory = async (
  eventId: string,
  category: string,
  numSeats: number
) => {
  try {
    const res = await api.get("/seat/findSeats", {
      params: { eventId: eventId, category: category, numSeats: numSeats },
    });
  } catch (error) {
    console.log(error);
  }
};

export const postConfirmSeats() = async ()
