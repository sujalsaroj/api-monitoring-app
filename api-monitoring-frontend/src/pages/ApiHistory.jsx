import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";
import toast from "react-hot-toast";

function ApiHistory() {
  const { id } = useParams();
  const navigate = useNavigate();
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

      setResult(response.data.content);

      setTotalPage(response.data.totalPages);
    } catch (error) {
      toast.error("Failed to Load Monitoring History");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, [id, page]);
  if (loading) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-slate-50">
        <p className="text-lg font-medium text-slate-600">Loading history...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-50">
      {/* Navbar */}

      {/* Main */}
      <main className="mx-auto max-w-7xl px-6 py-10 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-slate-900">
            Monitoring History
          </h1>

          <p className="mt-2 text-slate-500">
            View previous health checks for this API.
          </p>
        </div>

        {result.length === 0 ? (
          <div className="rounded-xl border border-dashed border-slate-300 bg-white py-16 text-center">
            <h3 className="text-lg font-semibold text-slate-800">
              No Monitoring Results Found
            </h3>

            <p className="mt-2 text-slate-500">
              Run an API check to generate monitoring history.
            </p>
          </div>
        ) : (
          <div className="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead className="border-b border-slate-200 bg-slate-50">
                  <tr>
                    <th className="px-6 py-4 text-sm font-semibold text-slate-600">
                      Status
                    </th>

                    <th className="px-6 py-4 text-sm font-semibold text-slate-600">
                      Status Code
                    </th>

                    <th className="px-6 py-4 text-sm font-semibold text-slate-600">
                      Response Time
                    </th>

                    <th className="px-6 py-4 text-sm font-semibold text-slate-600">
                      Checked At
                    </th>

                    <th className="px-6 py-4 text-sm font-semibold text-slate-600">
                      Error
                    </th>
                  </tr>
                </thead>

                <tbody className="divide-y divide-slate-200">
                  {result.map((item) => (
                    <tr key={item.id} className="transition hover:bg-slate-50">
                      <td className="px-6 py-4">
                        <span
                          className={`inline-flex rounded-full px-3 py-1 text-xs font-semibold ${
                            item.status === "UP"
                              ? "bg-green-100 text-green-700"
                              : item.status === "DOWN"
                                ? "bg-red-100 text-red-700"
                                : "bg-slate-100 text-slate-700"
                          }`}
                        >
                          {item.status}
                        </span>
                      </td>

                      <td className="px-6 py-4 text-sm text-slate-700">
                        {item.statusCode ?? "N/A"}
                      </td>

                      <td className="px-6 py-4 text-sm text-slate-700">
                        {item.responseTime != null
                          ? `${item.responseTime} ms`
                          : "N/A"}
                      </td>

                      <td className="px-6 py-4 text-sm text-slate-700">
                        {new Date(item.createAt).toLocaleString()}
                      </td>

                      <td className="max-w-xs px-6 py-4 text-sm">
                        {item.errorMessage ? (
                          <span className="text-red-600">
                            {item.errorMessage}
                          </span>
                        ) : (
                          <span className="text-slate-400">—</span>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* Pagination */}
        {totalPage > 0 && (
          <div className="mt-6 flex flex-col items-center justify-between gap-4 sm:flex-row">
            <p className="text-sm text-slate-500">
              Page{" "}
              <span className="font-medium text-slate-800">{page + 1}</span> of{" "}
              <span className="font-medium text-slate-800">{totalPage}</span>
            </p>

            <div className="flex gap-2">
              <button
                disabled={page === 0}
                onClick={() => setPage((prev) => prev - 1)}
                className="rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50 disabled:cursor-not-allowed disabled:opacity-40"
              >
                Previous
              </button>

              <button
                disabled={page + 1 >= totalPage}
                onClick={() => setPage((prev) => prev + 1)}
                className="rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50 disabled:cursor-not-allowed disabled:opacity-40"
              >
                Next
              </button>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}

export default ApiHistory;
