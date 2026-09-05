import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";
import toast from "react-hot-toast";

function ApiStats() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await api.get(`/api/apis/${id}/stats`);

        setStats(response.data);
      } catch (error) {
        toast.error("Failed to Fetch stats Data");
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, [id]);
  if (loading) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-slate-50">
        <p className="text-lg font-medium text-slate-600">
          Loading statistics...
        </p>
      </div>
    );
  }

  if (!stats) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-slate-50">
        <p className="text-lg font-medium text-slate-600">
          No statistics available.
        </p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-50">
      <main className="mx-auto max-w-7xl px-6 py-10 lg:px-8">
        <div className="mb-8">
          <p className="text-sm font-medium text-blue-600">API Statistics</p>

          <h1 className="mt-1 text-3xl font-bold tracking-tight text-slate-900">
            {stats.apiName}
          </h1>

          <p className="mt-2 text-slate-500">
            Health and performance summary for this monitored API.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 xl:grid-cols-4">
          {/* Latest Status */}
          <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
            <p className="text-sm font-medium text-slate-500">Latest Status</p>

            <div className="mt-4">
              <span
                className={`inline-flex rounded-full px-4 py-2 text-sm font-semibold ${
                  stats.latestStatus === "UP"
                    ? "bg-green-100 text-green-700"
                    : stats.latestStatus === "DOWN"
                      ? "bg-red-100 text-red-700"
                      : "bg-slate-100 text-slate-700"
                }`}
              >
                {stats.latestStatus}
              </span>
            </div>
          </div>

          {/* Uptime */}
          <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
            <p className="text-sm font-medium text-slate-500">Uptime</p>

            <p className="mt-3 text-3xl font-bold text-slate-900">
              {stats.upTimePercentage}%
            </p>

            <p className="mt-1 text-sm text-slate-400">
              Successful health checks
            </p>
          </div>

          {/* Average Response */}
          <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
            <p className="text-sm font-medium text-slate-500">
              Average Response Time
            </p>

            <p className="mt-3 text-3xl font-bold text-slate-900">
              {stats.averageResponseTime}
              <span className="ml-1 text-lg font-medium text-slate-500">
                ms
              </span>
            </p>

            <p className="mt-1 text-sm text-slate-400">
              Average across all checks
            </p>
          </div>

          {/* Total Checks */}
          <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
            <p className="text-sm font-medium text-slate-500">Total Checks</p>

            <p className="mt-3 text-3xl font-bold text-slate-900">
              {stats.totalChecks}
            </p>

            <p className="mt-1 text-sm text-slate-400">
              Monitoring results recorded
            </p>
          </div>
        </div>

        {/* Uptime visualization */}
        <div className="mt-6 rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
          <div className="flex items-center justify-between gap-4">
            <div>
              <h2 className="text-lg font-semibold text-slate-900">
                Uptime Performance
              </h2>

              <p className="mt-1 text-sm text-slate-500">
                Percentage of checks where the API returned the expected status.
              </p>
            </div>

            <p className="text-xl font-bold text-slate-900">
              {stats.upTimePercentage}%
            </p>
          </div>

          <div className="mt-5 h-3 overflow-hidden rounded-full bg-slate-100">
            <div
              className="h-full rounded-full bg-emerald-500 transition-all"
              style={{
                width: `${Math.min(Math.max(stats.upTimePercentage, 0), 100)}%`,
              }}
            />
          </div>
        </div>

        <div className="mt-8 flex flex-col gap-3 sm:flex-row">
          <button
            onClick={() => navigate(`/apis/${id}/history`)}
            className="rounded-lg bg-blue-600 px-5 py-2.5 font-medium text-white shadow-sm transition hover:bg-blue-700"
          >
            View History
          </button>

          <button
            onClick={() => navigate("/dashboard")}
            className="rounded-lg border border-slate-300 bg-white px-5 py-2.5 font-medium text-slate-700 transition hover:bg-slate-50"
          >
            Back to Dashboard
          </button>
        </div>
      </main>
    </div>
  );
}

export default ApiStats;
