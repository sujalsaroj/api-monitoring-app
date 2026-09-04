import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function EditApi() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [url, setUrl] = useState("");
  const [httpMethod, setHttpMethod] = useState("GET");
  const [expectedStatusCode, setExpectedStatusCode] = useState(200);
  const [checkInterval, setCheckInterval] = useState(60);
  const [timeout, setTimeout] = useState(5000);
  const [active, setActive] = useState(true);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchApi = async () => {
      try {
        const response = await api.get(`/api/apis/${id}`);
        console.log("EDIT API RESPONSE:", response.data);
        const data = response.data;
        setName(data.name);
        setUrl(data.url);
        setHttpMethod(data.httpMethod);
        setExpectedStatusCode(data.expectedStatusCode);
        setCheckInterval(data.checkInterval);
        setTimeout(data.timeout);
        setActive(data.active);
      } catch (error) {
        console.error("Failed to fetch API:", error);
        alert("Failed to load API");
      } finally {
        setLoading(false);
      }
    };
    fetchApi();
  }, [id]);
  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/apis/${id}`, {
        name,
        url,
        httpMethod,
        expectedStatusCode,
        checkInterval,
        timeout,
        active,
      });
      alert("API updated successfully");

      navigate("/dashboard");
    } catch (error) {
      console.error("Failed to update API:", error);
      alert("Failed to update API");
    }
  };
  if (loading) {
    return <p>Loading........</p>;
  }

  return (
    <>
      <div>
        <h1>Edit API</h1>

        <form onSubmit={handleUpdate}>
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
            value={expectedStatusCode}
            onChange={(e) => setExpectedStatusCode(Number(e.target.value))}
          />

          <input
            type="number"
            value={checkInterval}
            onChange={(e) => setCheckInterval(Number(e.target.value))}
          />

          <input
            type="number"
            value={timeout}
            onChange={(e) => setTimeout(Number(e.target.value))}
          />

          <label>
            Active
            <input
              type="checkbox"
              checked={active}
              onChange={(e) => setActive(e.target.checked)}
            />
          </label>

          <button type="submit">Update API</button>

          <button type="button" onClick={() => navigate("/dashboard")}>
            Cancel
          </button>
        </form>
      </div>
    </>
  );
}

export default EditApi;
