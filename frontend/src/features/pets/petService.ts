import { Pet, PetPayload } from "@entities/pet/model";
import { apiClient } from "@shared/api/apiClient";

export const petService = {
  list: () => apiClient<Pet[]>("/api/v1/pet"),
  create: (payload: PetPayload) =>
    apiClient<Pet>("/api/v1/pet/new", {
      method: "POST",
      data: payload
    }),
  update: (petId: string, payload: PetPayload) =>
    apiClient<Pet>(`/api/v1/pet/update/${petId}`, {
      method: "PUT",
      data: payload
    }),
  remove: (petId: string) =>
    apiClient<void>(`/api/v1/pet/delete/${petId}`, {
      method: "DELETE"
    })
};
