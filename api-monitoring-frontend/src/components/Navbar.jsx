import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <nav className="bg-slate-900 px-6 py-4 text-white">
      <div className="mx-auto flex max-w-7xl items-center justify-between">
        <h2
          onClick={() => navigate("/dashboard")}
          className="cursor-pointer text-xl font-bold"
        >
          API Monitor
        </h2>

        <div className="flex gap-3">
          <button
            onClick={() => navigate("/dashboard")}
            className="rounded px-3 py-2 hover:bg-slate-800"
          >
            Dashboard
          </button>

          <button
            onClick={() => navigate("/apis/add")}
            className="rounded px-3 py-2 hover:bg-slate-800"
          >
            Add API
          </button>

          <button
            onClick={handleLogout}
            className="rounded border border-slate-600 px-3 py-2 hover:bg-slate-800"
          >
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
