import { Route, Routes } from "react-router-dom";
import Dashboard from "../pages/Dashboard";
import Login from "../pages/Login";
import Register from "../pages/Register";
import ProtectedRouter from "../components/ProtectedRouter";
import AddApi from "../pages/AddApi";
import EditApi from "../pages/EditAPi";

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
    </Routes>
  );
}

export default AppRouter;
