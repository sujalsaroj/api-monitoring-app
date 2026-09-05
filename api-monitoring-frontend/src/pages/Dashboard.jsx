import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

function Dashboard() {
  const [apis, setApis] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchApis = async () => {
      try {
        const response = await api.get("/api/apis");

        setApis(response.data);
      } catch (error) {
      } finally {
        setLoading(false);
      }
    };

    fetchApis();
  }, []);

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this API?",
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await api.delete(`/api/apis/${id}`);

      setApis((previousApi) => previousApi.filter((api) => api.id !== id));

      toast.success("API deleted successfully");
    } catch (error) {
      toast.error("Failed to delete API");
    }
  };

  const handleCheck = async (id) => {
    try {
      const response = await api.post(`/api/apis/${id}/check`);
      const result = response.data;

      if (result.status === "UP") {
        toast.success(
          `API is UP • ${result.statusCode} • ${result.responseTime} ms`,
        );
      } else {
        toast.error(
          `API is DOWN • ${result.statusCode ?? "N/A"} • ${result.responseTime} ms`,
        );
      }
    } catch (error) {
      toast.error("Failed to check API");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (loading) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-slate-50">
        <p className="text-lg font-medium text-slate-600">Loading APIs...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-50">
      {/* Main */}

      <main className="mx-auto max-w-7xl px-6 py-10 lg:px-8">
        {/* Header */}

        <div className="mb-8 flex flex-col gap-5 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <h1 className="text-3xl font-bold tracking-tight text-slate-900">
              API Monitoring Dashboard
            </h1>

            <p className="mt-2 text-slate-500">
              Monitor, test and manage your APIs from one place.
            </p>
          </div>

          <button
            onClick={() => navigate("/apis/add")}
            className="rounded-lg bg-blue-600 px-5 py-2.5 font-medium text-white shadow-sm transition hover:bg-blue-700"
          >
            + Add API
          </button>
        </div>

        {/* Empty state */}

        {apis.length === 0 ? (
          <div className="rounded-xl border border-dashed border-slate-300 bg-white px-6 py-16 text-center shadow-sm">
            <h3 className="text-xl font-semibold text-slate-800">
              No APIs added yet
            </h3>

            <p className="mt-2 text-slate-500">
              Add your first API to start monitoring.
            </p>

            <button
              onClick={() => navigate("/apis/add")}
              className="mt-6 rounded-lg bg-blue-600 px-5 py-2.5 font-medium text-white transition hover:bg-blue-700"
            >
              Add API
            </button>
          </div>
        ) : (
          /* API Grid */

          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-3">
            {apis.map((api) => (
              <div
                key={api.id}
                className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition hover:-translate-y-1 hover:shadow-md"
              >
                {/* Card Header */}

                <div className="flex items-start justify-between gap-4">
                  <div>
                    <h3 className="text-xl font-semibold text-slate-900">
                      {api.name}
                    </h3>

                    <p className="mt-1 break-all text-sm text-slate-500">
                      {api.url}
                    </p>
                  </div>

                  <span
                    className={`rounded-full px-3 py-1 text-xs font-semibold ${
                      api.active
                        ? "bg-green-100 text-green-700"
                        : "bg-red-100 text-red-700"
                    }`}
                  >
                    {api.active ? "Active" : "Inactive"}
                  </span>
                </div>

                {/* Details */}

                <div className="mt-6 grid grid-cols-2 gap-4">
                  <div className="rounded-lg bg-slate-50 p-3">
                    <p className="text-xs text-slate-500">HTTP Method</p>

                    <p className="mt-1 font-semibold text-slate-800">
                      {api.httpMethod}
                    </p>
                  </div>

                  <div className="rounded-lg bg-slate-50 p-3">
                    <p className="text-xs text-slate-500">Expected Status</p>

                    <p className="mt-1 font-semibold text-slate-800">
                      {api.expectedStatusCode}
                    </p>
                  </div>

                  <div className="rounded-lg bg-slate-50 p-3">
                    <p className="text-xs text-slate-500">Check Interval</p>

                    <p className="mt-1 font-semibold text-slate-800">
                      {api.checkInterval}s
                    </p>
                  </div>

                  <div className="rounded-lg bg-slate-50 p-3">
                    <p className="text-xs text-slate-500">Timeout</p>

                    <p className="mt-1 font-semibold text-slate-800">
                      {api.timeout} ms
                    </p>
                  </div>
                </div>

                {/* Primary action */}

                <button
                  onClick={() => handleCheck(api.id)}
                  className="mt-6 w-full rounded-lg bg-emerald-600 px-4 py-2.5 font-medium text-white transition hover:bg-emerald-700"
                >
                  Check Now
                </button>

                {/* Other Actions */}

                <div className="mt-3 grid grid-cols-2 gap-2">
                  <button
                    onClick={() => navigate(`/apis/${api.id}/history`)}
                    className="rounded-lg border border-slate-300 px-3 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50"
                  >
                    History
                  </button>

                  <button
                    onClick={() => navigate(`/apis/${api.id}/stats`)}
                    className="rounded-lg border border-slate-300 px-3 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50"
                  >
                    Stats
                  </button>

                  <button
                    onClick={() => navigate(`/apis/edit/${api.id}`)}
                    className="rounded-lg border border-blue-200 px-3 py-2 text-sm font-medium text-blue-700 transition hover:bg-blue-50"
                  >
                    Edit
                  </button>

                  <button
                    onClick={() => handleDelete(api.id)}
                    className="rounded-lg border border-red-200 px-3 py-2 text-sm font-medium text-red-600 transition hover:bg-red-50"
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default Dashboard;
