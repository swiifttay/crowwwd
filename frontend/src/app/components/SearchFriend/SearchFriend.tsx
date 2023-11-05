"use client"

import { searchProfile, addFriend } from "../axios/apiService";

/* CURRENTLY NOT IN USE */
const SearchFriend = () => {

  const [searchVisible, setSearchVisible] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [selectedProfile, setSelectedProfile] = useState(null);
  function handleSearchClick() {
    setSearchVisible(true);
  }

  const handleSearchInputChange = (event) => {
    setSearchQuery(event.target.value);
  };
  /******** */

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await searchProfile(searchQuery);
      setSearchResults(response);
    } catch (error) {
      console.error("Error searching for usernames:", error);
    }
  };

  const handleAddFriend = async (friendId) => {
    try {
      const response = await addFriend(friendId);
      if (response.status === 200) {
        console.log("Friend added successfully");
      } else {
        console.error("Error adding friend:", response.data);
      }
    } catch (error) {
      console.error("Error adding friend:", error);
    }
  };

  return (
    <div className="flex">
      <div
        className={`hover-text-gray-300 cursor-pointer ${
          searchVisible ? "hidden" : ""
        }`}
        onClick={handleSearchClick}
      >
        Search Friend
      </div>
      {searchVisible && (
        <div className="">
          <form className={styles.searchForm} onSubmit={handleSearchSubmit}>
            <input
              type="text"
              value={searchQuery}
              onChange={handleSearchInputChange}
              placeholder="Search..."
              className={styles.searchInput}
            />
            <button type="submit" className={styles.searchButton}>
              <MagnifyingGlassIcon className="h-6 stroke-white hover-text-gray-300 cursor-pointer" />
            </button>
          </form>
          {searchResults.length > 0 && (
            <div>
              <ul>
                {searchResults.map((result) => (
                  <li key={result.username}>
                    {result.username}
                    <button
                      onClick={() => handleAddFriend(result.id)}
                      className="bg-blue-500 text-white p-2 rounded"
                    >
                      Add Friend
                    </button>
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default SearchFriend;