import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function AddApi() {
  const [name, setName] = useState("");
  const [url, setUrl] = useState("");
  const [httpMethod, setHttpMethod] = useState("GET");
  const [expectedStatusCode, setExpectedStatusCode] = useState(200);
  const [checkInterval, setCheckInterval] = useState(60);
  const [timeout, setTimeout] = useState(5000);
  const [active, setActive] = useState(true);

  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post("/api/apis", {
        name,
        url,
        httpMethod,
        expectedStatusCode,
        checkInterval,
        timeout,
        active,
      });
      console.log(response.data);
      alert("API added Successfully");
      navigate("/dashboard");
    } catch (error) {
      console.error("Failed to add API: " + error);
      alert("Failed to Add");
    }
  };
  return (
    <>
      <div>
        <h1>API Add Form</h1>

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="API Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <input
            type="url"
            placeholder="API URL"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            required
          />
          <select
            value={httpMethod}
            onChange={(e) => setHttpMethod(e.target.value)}
          >
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="PUT">PUT</option>
            <option value="DELETE">DELETE</option>
          </select>
          <input
            type="number"
            placeholder="Expected Status Code"
            value={expectedStatusCode}
            onChange={(e) => setExpectedStatusCode(Number(e.target.value))}
          />
          <input
            type="number"
            placeholder="Check Interval"
            value={checkInterval}
            onChange={(e) => setCheckInterval(Number(e.target.value))}
          />
          <input
            type="number"
            placeholder="Timeout"
            value={timeout}
            onChange={(e) => setTimeout(Number(e.target.value))}
          />
          <label>
            Active
            <input
              type="checkbox"
              checked={active}
              onChange={(e) => setActive(e.target.checked)}
            ></input>
          </label>

          <button type="submit">Add API</button>
          <button type="button" onClick={() => navigate("/dashboard")}>
            Cancel
          </button>
        </form>
      </div>
    </>
  );
}

export default AddApi;
