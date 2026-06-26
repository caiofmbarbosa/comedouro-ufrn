import { BarChart3, CalendarClock, Gauge, LogOut, PawPrint, LayoutDashboard, Dog } from "lucide-react";
import { ReactNode } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "@features/auth/AuthProvider";

const navItems = [
  { to: "/", label: "Dashboard", icon: LayoutDashboard },
  { to: "/pets", label: "Pets", icon: Dog },
  { to: "/historico", label: "Historico", icon: CalendarClock },
  { to: "/reservatorio", label: "Reservatorio", icon: Gauge },
  { to: "/indicadores", label: "Indicadores", icon: BarChart3 }
];

export function AppLayout({ children }: { children: ReactNode }) {
  const { logout } = useAuth();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/login");
  }

  return (
    <div className="grid min-h-screen grid-cols-1 bg-[#f6f7f6] lg:grid-cols-[280px_minmax(0,1fr)]">
      <aside className="flex h-auto flex-col gap-8 border-r border-neutral-300 bg-white p-6 lg:sticky lg:top-0 lg:h-screen">
        <div className="flex items-center gap-3 text-lg font-bold leading-tight">
          <PawPrint size={34} />
          <strong>Sistema de Alimentacao de Caes</strong>
        </div>

        <nav className="grid gap-2">
          {navItems.map((item) => {
            const Icon = item.icon;
            return (
              <NavLink
                key={item.to}
                to={item.to}
                className={({ isActive }) =>
                  `flex min-h-14 items-center gap-3 rounded-lg px-4 text-neutral-950 no-underline transition hover:bg-neutral-100 ${isActive ? "bg-neutral-200" : ""}`
                }
              >
                <Icon size={22} />
                <span>{item.label}</span>
              </NavLink>
            );
          })}
        </nav>

        <button
          className="mt-auto flex min-h-14 items-center gap-3 border-t border-neutral-300 px-4 text-left text-neutral-950 transition hover:bg-neutral-100"
          onClick={handleLogout}
        >
          <LogOut size={22} />
          <span>Sair</span>
        </button>
      </aside>

      <main className="p-5 md:p-8">{children}</main>
    </div>
  );
}
