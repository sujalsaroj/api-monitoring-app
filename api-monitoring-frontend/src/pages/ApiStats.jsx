import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function ApiStats() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await api.get(`/api/apis/${id}/stats`);
        console.log("STATS RESPONSE: ", response.data);
        setStats(response.data);
      } catch (error) {
        console.error("Failed to Fetch the stats: ", error);
        alert("Failed to Fetch stats Data");
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, [id]);
  if (loading) {
    return <p>Loading........</p>;
  }
  if (!stats) {
    <p>No Statistic Available</p>;
  }
  return (
    <div>
      <h1>{stats.apiName} Statistic</h1>

      <div>
        <h3>Latest Status</h3>
        <p>{stats.latestStatus}</p>
      </div>
      <div>
        <h3>upTime</h3>
        <p>{stats.upTimePercentage}%</p>
      </div>
      <div>
        <h3>AverageTIme</h3>
        <p>{stats.averageResponseTime}ms</p>
      </div>
      <div>
        <h3>TotalChecks</h3>
        <p>{stats.totalChecks}</p>
      </div>

      <button onClick={() => navigate(`/apis/${id}/history`)}>
        View History
      </button>

      <button onClick={() => navigate("/dashboard")}>Back to Dashboard</button>
    </div>
  );
}

export default ApiStats;
