import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const [apis, setApis] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  useEffect(() => {
    const fetchApis = async () => {
      try {
        const response = await api.get("/api/apis");
        console.log(response.data);
        setApis(response.data);
      } catch (error) {
        console.error("Failed to Fetch apis : " + error);
      } finally {
        setLoading(false);
      }
    };
    fetchApis();
  }, []);
  if (loading) {
    return <p>Loading.....</p>;
  }

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this api",
    );

    if (!confirmDelete) {
      return;
    }
    try {
      await api.delete(`/api/apis/${id}`);
      setApis((previousApi) => {
        previousApi.filter((api) => api.id !== id);
      });
      alert("API delete successfully");
    } catch (error) {
      console.error("Failed to delete api: " + error);
      alert("Failed to delete API");
    }
  };

  const handleCheck = async (id) => {
    try {
      const response = await api.post(`/api/apis/${id}/check`);

      console.log("CHECK RESULT:", response.data);

      alert(
        `Status: ${response.data.status}
Status Code: ${response.data.statusCode}
Response Time: ${response.data.responseTime} ms`,
      );
    } catch (error) {
      console.error("Failed to check API:", error);
      alert("Failed to check API");
    }
  };

  return (
    <>
      <div>
        <h1>API Monitoring DashBoard</h1>
        {apis.length === 0 ? (
          <p>No Api Added yet.</p>
        ) : (
          apis.map((api) => (
            <div key={api.id}>
              <h3>{api.name}</h3>
              <p>URL : {api.url}</p>
              <p>Http Method :{api.httpMethod}</p>
              <p>Expected status : {api.expectedStatusCode}</p>
              <p>Active :{api.active ? "Yes" : "NO"} </p>
              <button onClick={() => handleDelete(api.id)}>Delete</button>
              <button onClick={() => navigate(`/api/edit/${api.id}`)}>
                Edit
              </button>
              <button onClick={() => handleCheck(api.id)}>Check Now</button>
              <button onClick={() => navigate(`/apis/${api.id}/history`)}>
                History
              </button>
              <button onClick={() => navigate(`/apis/${api.id}/stats`)}>
                Stats
              </button>
            </div>
          ))
        )}
        <button onClick={() => navigate("/addApi")}>Add API</button>
      </div>
    </>
  );
}

export default Dashboard;
