import { AlimentacaoLog } from "@entities/alimentacao/model";
import { Reservatorio } from "@entities/reservatorio/model";

export function getTodayLogs(logs: AlimentacaoLog[]) {
  const today = new Date().toISOString().slice(0, 10);
  return logs.filter((log) => log.dataHora.startsWith(today));
}

export function getLastDaysLogs(logs: AlimentacaoLog[], days: number) {
  const start = new Date();
  start.setDate(start.getDate() - days + 1);
  start.setHours(0, 0, 0, 0);
  return logs.filter((log) => new Date(log.dataHora) >= start);
}

export function sumGrams(logs: AlimentacaoLog[]) {
  return logs.reduce((total, log) => total + log.gramasLiberadas, 0);
}

export function getLastLog(logs: AlimentacaoLog[]) {
  return [...logs].sort((a, b) => new Date(b.dataHora).getTime() - new Date(a.dataHora).getTime())[0];
}

export function buildHourlyBars(logs: AlimentacaoLog[]) {
  const buckets = [0, 6, 12, 18].map((hour) => ({ hour, grams: 0 }));
  logs.forEach((log) => {
    const hour = new Date(log.dataHora).getHours();
    const bucket = Math.min(3, Math.floor(hour / 6));
    buckets[bucket].grams += log.gramasLiberadas;
  });
  return buckets;
}

export function estimateReservoirByLog(logs: AlimentacaoLog[], reservatorio: Reservatorio | null) {
  if (!reservatorio) {
    return new Map<string, number>();
  }

  let remaining = reservatorio.gramasRestantes;
  const values = new Map<string, number>();

  [...logs]
    .sort((a, b) => new Date(b.dataHora).getTime() - new Date(a.dataHora).getTime())
    .forEach((log) => {
      values.set(log.id, remaining);
      remaining += log.gramasLiberadas;
    });

  return values;
}
