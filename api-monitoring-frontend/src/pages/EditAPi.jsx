import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";
import toast from "react-hot-toast";

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

        const data = response.data;
        setName(data.name);
        setUrl(data.url);
        setHttpMethod(data.httpMethod);
        setExpectedStatusCode(data.expectedStatusCode);
        setCheckInterval(data.checkInterval);
        setTimeout(data.timeout);
        setActive(data.active);
      } catch (error) {
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
      toast.success("API updated successfully");

      navigate("/dashboard");
    } catch (error) {
      toast.success("Failed to update API");
    }
  };
  if (loading) {
    return <p>Loading........</p>;
  }

  return (
    <div className="min-h-screen bg-slate-50">
      <main className="mx-auto max-w-3xl px-6 py-10">
        <div className="rounded-2xl border border-slate-200 bg-white p-8 shadow-sm">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-slate-900">Edit API</h1>

            <p className="mt-2 text-slate-500">
              Update your API monitoring configuration.
            </p>
          </div>

          <form onSubmit={handleUpdate} className="space-y-6">
            <div>
              <label className="mb-2 block text-sm font-medium text-slate-700">
                API Name
              </label>

              <input
                type="text"
                placeholder="API Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
              />
            </div>

            <div>
              <label className="mb-2 block text-sm font-medium text-slate-700">
                API URL
              </label>

              <input
                type="url"
                placeholder="https://example.com/api"
                value={url}
                onChange={(e) => setUrl(e.target.value)}
                required
                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
              />
            </div>

            <div>
              <label className="mb-2 block text-sm font-medium text-slate-700">
                HTTP Method
              </label>

              <select
                value={httpMethod}
                onChange={(e) => setHttpMethod(e.target.value)}
                className="w-full rounded-lg border border-slate-300 bg-white px-4 py-3 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
              >
                <option value="GET">GET</option>
                <option value="POST">POST</option>
                <option value="PUT">PUT</option>
                <option value="DELETE">DELETE</option>
              </select>
            </div>

            <div className="grid grid-cols-1 gap-5 md:grid-cols-3">
              <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                  Expected Status
                </label>

                <input
                  type="number"
                  value={expectedStatusCode}
                  onChange={(e) =>
                    setExpectedStatusCode(Number(e.target.value))
                  }
                  className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
                />
              </div>

              <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                  Check Interval
                </label>

                <input
                  type="number"
                  min="1"
                  value={checkInterval}
                  onChange={(e) => setCheckInterval(Number(e.target.value))}
                  className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
                />

                <p className="mt-1 text-xs text-slate-400">Seconds</p>
              </div>

              <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                  Timeout
                </label>

                <input
                  type="number"
                  min="100"
                  value={timeout}
                  onChange={(e) => setTimeout(Number(e.target.value))}
                  className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
                />

                <p className="mt-1 text-xs text-slate-400">Milliseconds</p>
              </div>
            </div>

            <div className="flex items-center justify-between rounded-lg border border-slate-200 bg-slate-50 p-4">
              <div>
                <p className="font-medium text-slate-800">Enable Monitoring</p>

                <p className="text-sm text-slate-500">
                  Automatically monitor this API based on the configured
                  interval.
                </p>
              </div>

              <input
                type="checkbox"
                checked={active}
                onChange={(e) => setActive(e.target.checked)}
                className="h-5 w-5 cursor-pointer accent-blue-600"
              />
            </div>

            <div className="flex flex-col-reverse gap-3 pt-2 sm:flex-row sm:justify-end">
              <button
                type="button"
                onClick={() => navigate("/dashboard")}
                className="rounded-lg border border-slate-300 px-5 py-2.5 font-medium text-slate-700 transition hover:bg-slate-50"
              >
                Cancel
              </button>

              <button
                type="submit"
                className="rounded-lg bg-blue-600 px-6 py-2.5 font-medium text-white shadow-sm transition hover:bg-blue-700"
              >
                Update API
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
}

export default EditApi;
