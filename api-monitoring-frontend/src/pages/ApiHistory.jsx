import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

function ApiHistory() {
  const { id } = useParams();

  const [result, setResult] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetchHistory = async () => {
    try {
      setLoading(true);

      const response = await api.get(
        `/api/apis/${id}/results?page=${page}&size=10`,
      );

      console.log("History Response:", response.data);

      setResult(response.data.content);

      setTotalPage(response.data.totalPages);
    } catch (error) {
      console.error("Failed to Fetch History:", error);

      alert("Failed to Load Monitoring History");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, [id, page]);

  if (loading) {
    return <p>Loading........</p>;
  }

  return (
    <div>
      <h2>Monitoring History</h2>

      {result.length === 0 ? (
        <p>No Monitoring Result Found</p>
      ) : (
        result.map((result) => (
          <div key={result.id}>
            <p>Status: {result.status}</p>

            <p>Status Code: {result.statusCode ?? "N/A"}</p>

            <p>Response Time: {result.responseTime} ms</p>

            <p>Checked At: {result.createAt}</p>

            {result.errorMessage && <p>Error: {result.errorMessage}</p>}

            <hr />
          </div>
        ))
      )}

      <button disabled={page === 0} onClick={() => setPage((prev) => prev - 1)}>
        Previous
      </button>

      <span>
        {" "}
        Page {page + 1} of {totalPage}{" "}
      </span>

      <button
        disabled={page + 1 >= totalPage}
        onClick={() => setPage((prev) => prev + 1)}
      >
        Next
      </button>
    </div>
  );
}

export default ApiHistory;
