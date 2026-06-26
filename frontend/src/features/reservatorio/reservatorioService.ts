import { Reservatorio } from "@entities/reservatorio/model";
import { apiClient } from "@shared/api/apiClient";

export const reservatorioService = {
  get: () => apiClient<Reservatorio>("/api/v1/reservatorio")
};
