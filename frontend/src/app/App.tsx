import { Navigate, Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { AuthProvider, useAuth } from "@features/auth/AuthProvider";
import { DashboardPage } from "@pages/dashboard/DashboardPage";
import { HistoricoPage } from "@pages/historico/HistoricoPage";
import { IndicadoresPage } from "@pages/indicadores/IndicadoresPage";
import { LoginPage } from "@pages/login/LoginPage";
import { PetsPage } from "@pages/pets/PetsPage";
import { ReservatorioPage } from "@pages/reservatorio/ReservatorioPage";
import { AppLayout } from "@widgets/layout/AppLayout";

function PrivateRoutes() {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return (
    <AppLayout>
      <Routes>
        <Route path="/" element={<DashboardPage />} />
        <Route path="/pets" element={<PetsPage />} />
        <Route path="/historico" element={<HistoricoPage />} />
        <Route path="/reservatorio" element={<ReservatorioPage />} />
        <Route path="/indicadores" element={<IndicadoresPage />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </AppLayout>
  );
}

export function App() {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/*" element={<PrivateRoutes />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
}
