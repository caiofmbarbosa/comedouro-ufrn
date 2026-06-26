import { CalendarDays, Clock3, Gauge, Scale, Utensils } from "lucide-react";
import { getLastDaysLogs, getLastLog, getTodayLogs, buildHourlyBars, sumGrams } from "@features/dashboard/metrics";
import { useDashboardData } from "@features/dashboard/useDashboardData";
import { formatGrams, percentage } from "@shared/lib/number";
import { formatTime } from "@shared/lib/date";
import { EmptyState, ErrorState, LoadingState } from "@shared/ui/State";
import { PetSelector } from "@widgets/PetSelector";

export function DashboardPage() {
  const { pets, selectedPetId, setSelectedPetId, logs, reservatorio, isLoading, error } = useDashboardData();
  const todayLogs = getTodayLogs(logs);
  const weekLogs = getLastDaysLogs(logs, 7);
  const lastLog = getLastLog(logs);
  const bars = buildHourlyBars(todayLogs);
  const maxBar = Math.max(...bars.map((bar) => bar.grams), 100);
  const reservoirPercentage = reservatorio ? percentage(reservatorio.gramasRestantes, reservatorio.capacidadeTotal) : 0;

  if (isLoading) {
    return <LoadingState />;
  }

  return (
    <div className="grid gap-6">
      <header className="flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
        <div>
          <h1 className="text-3xl font-bold">Ola, Tutor!</h1>
          <p className="mt-1 text-neutral-600">Aqui esta um resumo da alimentacao do seu cao.</p>
        </div>
        {pets.length > 0 && <PetSelector pets={pets} value={selectedPetId} onChange={setSelectedPetId} />}
      </header>

      {error && <ErrorState message={error} />}
      {pets.length === 0 && <EmptyState message="Cadastre um pet para iniciar o acompanhamento alimentar." />}

      {pets.length > 0 && (
        <>
          <section className="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
            <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
              <Utensils size={24} />
              <span className="text-neutral-600">Consumo Hoje</span>
              <strong className="text-3xl">{formatGrams(sumGrams(todayLogs))}</strong>
              <small className="text-neutral-500">Total de gramas liberadas</small>
            </article>
            <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
              <Clock3 size={24} />
              <span className="text-neutral-600">Ultima Liberacao</span>
              <strong className="text-3xl">{lastLog ? formatTime(lastLog.dataHora) : "--:--"}</strong>
              <small className="text-neutral-500">{lastLog ? `${lastLog.gramasLiberadas} g liberadas` : "Sem eventos"}</small>
            </article>
            <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
              <Gauge size={24} />
              <span className="text-neutral-600">Reservatorio Estimado</span>
              <strong className="text-3xl">{formatGrams(reservatorio?.gramasRestantes || 0)}</strong>
              <small className="text-neutral-500">{reservoirPercentage}% da capacidade</small>
            </article>
            <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
              <CalendarDays size={24} />
              <span className="text-neutral-600">Total de Eventos Hoje</span>
              <strong className="text-3xl">{todayLogs.length}</strong>
              <small className="text-neutral-500">Eventos registrados</small>
            </article>
          </section>

          <section className="rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
            <div className="mb-5 flex items-center gap-2">
              <h2 className="text-xl font-bold">Resumo do Consumo</h2>
              <span className="text-neutral-600">gramas liberadas hoje</span>
            </div>
            <div className="grid h-72 grid-cols-4 items-end border-b border-neutral-950 bg-[repeating-linear-gradient(to_top,transparent_0,transparent_55px,#d7d9d6_56px)] px-3 pt-6">
              {bars.map((bar) => (
                <div className="grid h-full items-end justify-items-center gap-3" key={bar.hour}>
                  <div className="min-h-2 w-11 rounded-t-md bg-gradient-to-b from-neutral-700 to-neutral-300" style={{ height: `${Math.max(8, (bar.grams / maxBar) * 100)}%` }} />
                  <span>{String(bar.hour).padStart(2, "0")}:00</span>
                </div>
              ))}
            </div>
          </section>

          <section className="grid gap-5 rounded-lg border border-neutral-300 bg-white p-6 shadow-soft md:grid-cols-3">
            <article className="flex items-center gap-4">
              <Scale size={34} />
              <div>
                <strong className="block text-2xl">{formatGrams(sumGrams(weekLogs))}</strong>
                <span className="text-neutral-600">Consumo dos ultimos 7 dias</span>
              </div>
            </article>
            <article className="flex items-center gap-4">
              <Clock3 size={34} />
              <div>
                <strong className="block text-2xl">{lastLog ? formatTime(lastLog.dataHora) : "--:--"}</strong>
                <span className="text-neutral-600">Ultima liberacao</span>
              </div>
            </article>
            <article className="flex items-center gap-4">
              <CalendarDays size={34} />
              <div>
                <strong className="block text-2xl">{weekLogs.length}</strong>
                <span className="text-neutral-600">Total de eventos nos ultimos 7 dias</span>
              </div>
            </article>
          </section>
        </>
      )}
    </div>
  );
}
