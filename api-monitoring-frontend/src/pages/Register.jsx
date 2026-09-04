import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/auth/register", {
        name,
        email,
        password,
      });
      console.log(response.data);
      alert("Registration Successfully");
      navigate("/login");
    } catch (error) {
      console.error(error);
      alert("Registration failed");
    }
  };
  return (
    <>
      <div>
        <h1>Registration</h1>
        <form onSubmit={handleRegister}>
          <input
            type="text"
            placeholder="Enter your Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          ></input>
          <input
            type="email"
            placeholder="Enter Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          ></input>
          <input
            type="password"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          ></input>
          <button type="submit">Register</button>
          <p>
            Already have an Account <Link to="/login">Login</Link>
          </p>
        </form>
      </div>
    </>
  );
}
export default Register;
