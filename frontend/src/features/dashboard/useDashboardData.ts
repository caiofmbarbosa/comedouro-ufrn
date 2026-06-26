import { useEffect, useMemo, useState } from "react";
import { AlimentacaoLog } from "@entities/alimentacao/model";
import { Pet } from "@entities/pet/model";
import { Reservatorio } from "@entities/reservatorio/model";
import { alimentacaoService } from "@features/alimentacao/alimentacaoService";
import { petService } from "@features/pets/petService";
import { reservatorioService } from "@features/reservatorio/reservatorioService";

export function useDashboardData() {
  const [pets, setPets] = useState<Pet[]>([]);
  const [selectedPetId, setSelectedPetId] = useState("");
  const [logs, setLogs] = useState<AlimentacaoLog[]>([]);
  const [reservatorio, setReservatorio] = useState<Reservatorio | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  async function loadBaseData() {
    setIsLoading(true);
    setError("");
    try {
      const [petList, reservoir] = await Promise.all([petService.list(), reservatorioService.get()]);
      setPets(petList);
      setReservatorio(reservoir);
      setSelectedPetId((current) => current || petList[0]?.id || "");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Nao foi possivel carregar os dados.");
    } finally {
      setIsLoading(false);
    }
  }

  async function loadLogs(petId: string) {
    if (!petId) {
      setLogs([]);
      return;
    }

    try {
      const petLogs = await alimentacaoService.listByPet(petId);
      setLogs(petLogs.sort((a, b) => new Date(b.dataHora).getTime() - new Date(a.dataHora).getTime()));
    } catch (err) {
      setError(err instanceof Error ? err.message : "Nao foi possivel carregar o historico.");
    }
  }

  useEffect(() => {
    loadBaseData();
  }, []);

  useEffect(() => {
    loadLogs(selectedPetId);
  }, [selectedPetId]);

  const selectedPet = useMemo(() => pets.find((pet) => pet.id === selectedPetId) || null, [pets, selectedPetId]);

  return {
    pets,
    selectedPet,
    selectedPetId,
    setSelectedPetId,
    logs,
    reservatorio,
    isLoading,
    error,
    refresh: async () => {
      await loadBaseData();
      await loadLogs(selectedPetId);
    }
  };
}
