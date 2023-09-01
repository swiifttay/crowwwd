import DataEntry from "../components/Login/DataEntry";

export default function RegisterForm() {
  return (
    <form className="mt-8 w-full max-w-sm">
      <div className="flex space-x-2">
        <DataEntry type="text" id="firstName" placeholder="First Name" />
        <DataEntry type="text" id="lastName" placeholder="Last Name" />
      </div>
      <DataEntry type="text" id="username" placeholder="Username/email" />
      <DataEntry type="password" id="password" placeholder="Password" />

      <button
        type="submit"
        className="mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue"
      >
        Sign Up
      </button>
    </form>
  );
}
