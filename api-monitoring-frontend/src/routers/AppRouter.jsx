import { Route, Routes } from "react-router-dom";
import Dashboard from "../pages/Dashboard";
import Login from "../pages/Login";
import Register from "../pages/Register";
import ProtectedRouter from "../components/ProtectedRouter";
import AddApi from "../pages/AddApi";
import EditApi from "../pages/EditAPi";
import ApiHistory from "../pages/ApiHistory";
import MainLayout from "../layouts/MainLayout";
import ApiStats from "../pages/ApiStats";

function AppRouter() {
  return (
    <Routes>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/register" element={<Register />}></Route>
      <Route
        element={
          <ProtectedRouter>
            <MainLayout />
          </ProtectedRouter>
        }
      >
        <Route path="/dashboard" element={<Dashboard />} />

        <Route path="/apis/add" element={<AddApi />} />

        <Route path="/apis/edit/:id" element={<EditApi />} />

        <Route path="/apis/:id/history" element={<ApiHistory />} />

        <Route path="/apis/:id/stats" element={<ApiStats />} />
      </Route>
    </Routes>
  );
}

export default AppRouter;
