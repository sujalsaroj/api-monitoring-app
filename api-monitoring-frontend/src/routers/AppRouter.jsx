import { Route, Routes } from "react-router-dom";
import Dashboard from "../pages/Dashboard";
import Login from "../pages/Login";
import Register from "../pages/Register";
import ProtectedRouter from "../components/ProtectedRouter";
import AddApi from "../pages/AddApi";
import EditApi from "../pages/EditAPi";
import ApiHistory from "../pages/ApiHistory";
import ApiStats from "../pages/ApiStats";

function AppRouter() {
  return (
    <Routes>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/register" element={<Register />}></Route>
      <Route
        path="/dashboard"
        element={
          <ProtectedRouter>
            <Dashboard />
          </ProtectedRouter>
        }
      ></Route>
      <Route
        path="/addApi"
        element={
          <ProtectedRouter>
            <AddApi />
          </ProtectedRouter>
        }
      ></Route>
      <Route
        path="/api/edit/:id"
        element={
          <ProtectedRouter>
            <EditApi />
          </ProtectedRouter>
        }
      ></Route>
      <Route
        path="/apis/:id/history"
        element={
          <ProtectedRouter>
            <ApiHistory />
          </ProtectedRouter>
        }
      ></Route>
      <Route
        path="/apis/:id/stats"
        element={
          <ProtectedRouter>
            <ApiStats />
          </ProtectedRouter>
        }
      ></Route>
    </Routes>
  );
}

export default AppRouter;
