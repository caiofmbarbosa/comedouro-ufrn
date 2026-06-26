import { apiClient } from "@shared/api/apiClient";
import { authStorage } from "@shared/api/storage";

type AuthResponse = {
  accessToken: string;
};

export type LoginPayload = {
  email: string;
  password: string;
};

export type RegisterPayload = LoginPayload & {
  nome: string;
  confirmPassword: string;
};

function persistSession(response: AuthResponse, user: { nome: string; email: string }) {
  authStorage.setSession(response.accessToken, "", {
    nome: user.nome,
    email: user.email
  });
  return response;
}

export const authService = {
  login: (payload: LoginPayload) =>
    apiClient<AuthResponse>("/api/v1/auth/login", {
      method: "POST",
      data: payload,
      skipAuth: true
    }).then((response) => persistSession(response, { nome: "Tutor", email: payload.email })),
  register: (payload: RegisterPayload) =>
    apiClient<AuthResponse>("/api/v1/auth/register", {
      method: "POST",
      data: payload,
      skipAuth: true
    }).then((response) => persistSession(response, { nome: payload.nome, email: payload.email })),
  logout: () => authStorage.clear()
};
