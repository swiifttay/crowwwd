"use client";

<<<<<<< Updated upstream
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
=======
>>>>>>> Stashed changes
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import {
  getArtistById,
  getFanRecords,
  getSpotifyLogin,
  getSpotifyToken,
  getUserProfile,
  updateFanRecords,
} from "../axios/apiService";
import UserFriends from "../components/SearchFriend/UserFriends";
import VerticalCard from "../components/SearchFriend/VerticalCard";
import Modal from "../components/UserProfile/Modal";
import EventButtonLong from "./EventButtonLong";
import EventButtonShort from "./EventButtonShort";
<<<<<<< Updated upstream
=======
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
>>>>>>> Stashed changes
import { useUserDetails } from "../contexts/UserDetailsContext";

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  phoneNo: string;
  userCreationDate: string;
  countryOfResidence: string;
  city: string;
  state: string;
  // dateOfBirth: string;
  address: string;
  postalCode: string;
  isPreferredMarketing: string;
  spotifyAccount: string;
}

export interface FanRecord {
  id: string;
  userId: string;
  artistId: string;
  registerDate: string;
}

export interface Artist {
  id: string;
  name: string;
  website: string;
  artistImage: string;
  artistImageURL: string;
  description: string;
}

export default function UserProfile() {
  const {user, setUser} = useUserDetails();
  const [fanRecords, setFanRecords] = useState<FanRecord[]>();
  const [favArtist, setFavArtist] = useState<Artist[]>();

  const [msg, setMsg] = useState("");
  const [isLoggedInSpotify, setIsLoggedInSpotify] = useState(false);
  const [isArtistLoaded, setIsArtistLoaded] = useState(false);
  const [spotifyButtonMsg, setSpotifyButtonMsg] = useState("");

  const router = useRouter();

  useEffect(() => {
    if (!localStorage.getItem("token")) {
      router.push("/login");
    }
    checkSpotifyLoginStatus();
    fetchUser();
    fetchFanRecords();
    // console.log(fanRecords);
  }, []);

  const fetchUser = async () => {
    const response = await getUserProfile();
    console.log(response);

    if (response.request?.status === 200) {
      setUser(response.data.user);
    } else {
      router.push("/login");
    }
  };

  const checkSpotifyLoginStatus = async () => {
    const spotifyTokenResponse = await getSpotifyToken();
    if (
      spotifyTokenResponse?.status === 200 &&
      spotifyTokenResponse.data?.response != null
    ) {
      console.log("success");
      localStorage.setItem("spotifyToken", spotifyTokenResponse.data.response);
      setSpotifyButtonMsg("Update My Records");
      setIsLoggedInSpotify(true);
    } else {
      console.log("failure");
      setSpotifyButtonMsg("Connect to Spotify");
      setIsLoggedInSpotify(false);
    }
  };

  const handleSpotifyButton = async () => {
    if (isLoggedInSpotify) {
      const updateFanRecordsResponse = await updateFanRecords();
      if (updateFanRecordsResponse.request?.status === 200) {
        window.location.reload();
      } else {
        localStorage.removeItem("spotifyToken");
        setIsLoggedInSpotify(false);
        setSpotifyButtonMsg("Connect to Spotify");
      }
    } else {
      const getAccountResponse = await getSpotifyLogin();
      if (getAccountResponse.request?.status == 200) {
        window.location.replace(getAccountResponse?.data);
      } else {
        if (!localStorage.getItem("token")) {
          router.push("/login");
        }
      }
    }
  };

  const fetchFanRecords = async () => {
    try {
      const response = await getFanRecords();

      // check if it user has logged to spotify
      if (response.status == 200) {
        const fanRecordsData: FanRecord[] = response.data.allFanRecords;

        // console.log(fanRecordsData.length);
        // if there is any fanrecord data
        if (fanRecordsData.length !== 0) {
          setFanRecords(fanRecordsData);

          // get all the artists that are relevant
          const artistResponses = await Promise.all(
            fanRecordsData.map(async (fanRecord: FanRecord) => {
              const artistResponse = await getArtistById(fanRecord.artistId);
              return artistResponse?.data.artist;
            }),
          );
          const flattenedArtistResponses = artistResponses.flat();

          setFavArtist((prev: Artist[] | undefined) => {
            const updatedFavArtist: Artist[] = prev ? [...prev] : [];

            // Add the artist responses to the existing list
            updatedFavArtist.push(...flattenedArtistResponses);
            setIsArtistLoaded(true);

            return updatedFavArtist;
          });
        } else {
          setMsg("Connect to Spotify to see your favourite artists!");
        }
      } else {
        setMsg("Connect to Spotify to see your favourite artists!");
      }
    } catch (error) {
      console.error("Error fetching fan records:", error);
    }
  };

  const handleUpdateProfile = async () => {
    router.push("/updateprofile");
  };
  const [isOpen, setIsOpen] = useState(false);
  const [overlayOpacity, setOverlayOpacity] = useState(0);

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = "hidden";
      setOverlayOpacity(0.6);
    } else {
      document.body.style.overflow = "auto";
      setOverlayOpacity(0);
    }
  }, [isOpen]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.reload();
  };

  return (
    <main className="flex flex-col items-center h-fit relative w-full px-8">
      <div className="flex flex-col justify-center align-center mt-4 w-full">
        <div className="flex flex-row justify-between w-full">
          <div className="flex flex-col sm:w-full lg:w-2/3">
            <div className="flex gap-12">
              <div className="">
                <div className="text-3xl font-bold mt-8 mb-4">
                  {user?.firstName} {user?.lastName}
                </div>
                <div className="text-md">{user?.username}</div>
                <div className="text-md">{user?.email}</div>
                <button
                  type="submit"
                  className="mt-6 w-full hover:bg-theme-light-blue text-white py-2 px-4 rounded-lg bg-theme-blue"
                  onClick={handleUpdateProfile}
                >
                  Update Profile
                </button>
                <div
                  className="mt-6 hover:underline hover:text-sky-400 text-theme-light-blue cursor-pointer"
                  onClick={handleLogout}
                >
                  Logout
                </div>
              </div>

              <div className="flex items-center justify-center flex-col">
                <AccountCircleIcon
                  style={{ fontSize: 140, color: "#e5e7eb" }}
                />
              </div>
            </div>
            <div className="flex flex-row justify-between mb-4 mt-20">
              <div className="text-xl font-bold w-1/2">
                Your favourite artists
              </div>
              <button
                className="bg-green-900 hover:bg-green-800 text-white text-center px-6 py-2 rounded-lg"
                onClick={handleSpotifyButton}
              >
                {spotifyButtonMsg}
              </button>
            </div>
            <div className="flex overflow-x-auto max-w-full">
              <div className="flex gap-5 overflow-x-auto max-w-2xl h-full px-4 py-8">
                <div className={`${isArtistLoaded ? "hidden" : "display"}`}>
                  {" "}
                  Connect to spotify to view your favourite artists!{" "}
                </div>
                {favArtist
                  ?.slice(0, Math.min(10, favArtist.length))
                  .map((artist, i) => {
                    return (
                      <VerticalCard
                        key={i}
                        image={artist.artistImageURL}
                        firstName={artist.name}
                        lastName=""
                      />
                    );
                  })}
              </div>
            </div>
          </div>

          <div className="ml-16 w-1/3">
            <div className="text-xl font-bold mt-6 mb-4">What you may like</div>
            <div className="flex flex-col gap-3">
              <EventButtonShort
                image="/images/ErasTour.jpg"
                title="The Eras Tour"
                artist="Taylor Swift"
              />
              <EventButtonShort
                image="/images/SingularTour.jpg"
                title="The Singular Tour"
                artist="Sabrina Carpenter"
              />
              <EventButtonShort
                image="/images/QueenofHearts.jpg"
                title="Queen of Hearts"
                artist="G.E.M"
              />
              <EventButtonShort
                image="/images/CarnivalWorldTour.jpg"
                title="CarnivalWorldTour"
                artist="Jay Chou"
              />
              <EventButtonShort
                image="/images/Mamamoo.jpg"
                title="My Con"
                artist="Mamamoo"
              />
            </div>
          </div>
        </div>
      </div>

      <div className="flex flex-col w-full">
        <div className="text-xl font-bold mt-16 mb-4">
          Your purchased concerts
        </div>

        <div className="flex flex-col gap-3">
          <div
            style={{
              position: "fixed",
              top: 0,
              left: 0,
              width: "100%",
              height: "100%",
              background: "black",
              zIndex: 40,
              transition: "opacity 0.3s",
              pointerEvents: "none",
              opacity: overlayOpacity,
            }}
            onClick={() => setIsOpen(false)}
          ></div>
          <div className="z-50">
            {isOpen && (
              <Modal
                title="Reputation Tour"
                artist="Taylor Swift"
                datetime="Fri 15 Sep 2023, 7pm"
                venue="The Star Theatre, The Star Performing Arts Centre"
                setIsOpen={setIsOpen}
              />
            )}
          </div>
          <EventButtonLong
            image="/images/TaylorSwift.jpg"
            title="Reputation Tour"
            artist="Taylor Swift"
            datetime="Fri 15 Sep 2023, 7pm"
            venue="The Star Theatre, The Star Performing Arts Centre"
            setIsOpen={setIsOpen}
          />
        </div>
      </div>
      <UserFriends />
    </main>
  );
}
