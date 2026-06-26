import axios, { AxiosRequestConfig } from "axios";
import { authStorage } from "./storage";

type ApiRequestConfig = AxiosRequestConfig & {
  skipAuth?: boolean;
};

type ApiErrorResponse = {
  message?: string;
  error?: string;
};

export class ApiError extends Error {
  constructor(
    message: string,
    public status: number
  ) {
    super(message);
  }
}

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "",
  headers: {
    "Content-Type": "application/json"
  }
});

api.interceptors.request.use((config) => {
  const token = authStorage.getAccessToken();
  if (token && !(config as ApiRequestConfig).skipAuth) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export async function apiClient<T>(path: string, config: ApiRequestConfig = {}): Promise<T> {
  try {
    const response = await api.request<T>({
      url: path,
      ...config
    });
    return response.data;
  } catch (error) {
    if (axios.isAxiosError<ApiErrorResponse>(error)) {
      const message =
        error.response?.data?.message ||
        error.response?.data?.error ||
        "Nao foi possivel concluir a operacao.";
      throw new ApiError(message, error.response?.status || 0);
    }
    throw error;
  }
}
