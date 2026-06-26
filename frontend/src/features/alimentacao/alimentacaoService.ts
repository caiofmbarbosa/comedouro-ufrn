import { AlimentacaoLog, AlimentacaoPayload } from "@entities/alimentacao/model";
import { apiClient } from "@shared/api/apiClient";

export const alimentacaoService = {
  listByPet: (petId: string) => apiClient<AlimentacaoLog[]>(`/api/v1/logs/${petId}`),
  create: (petId: string, payload: AlimentacaoPayload) =>
    apiClient<AlimentacaoLog>(`/api/v1/logs/${petId}/new`, {
      method: "POST",
      data: payload
    }),
  remove: (logId: string) =>
    apiClient<void>(`/api/v1/logs/${logId}/delete`, {
      method: "DELETE"
    })
};
