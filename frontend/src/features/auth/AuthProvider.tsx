import { createContext, ReactNode, useContext, useMemo, useState } from "react";
import { authStorage, StoredUser } from "@shared/api/storage";
import { authService, LoginPayload, RegisterPayload } from "./authService";

type AuthContextValue = {
  user: StoredUser | null;
  isAuthenticated: boolean;
  login: (payload: LoginPayload) => Promise<void>;
  register: (payload: RegisterPayload) => Promise<void>;
  logout: () => void;
};

const AuthContext = createContext<AuthContextValue | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<StoredUser | null>(() => authStorage.getUser());

  const value = useMemo<AuthContextValue>(
    () => ({
      user,
      isAuthenticated: Boolean(authStorage.getAccessToken()),
      login: async (payload) => {
        await authService.login(payload);
        setUser({ nome: "Tutor", email: payload.email });
      },
      register: async (payload) => {
        await authService.register(payload);
        setUser({ nome: payload.nome, email: payload.email });
      },
      logout: () => {
        authService.logout();
        setUser(null);
      }
    }),
    [user]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
}
