import { BarChart3, CalendarDays, Gauge, Scale } from "lucide-react";
import { getLastDaysLogs, getLastLog, sumGrams } from "@features/dashboard/metrics";
import { useDashboardData } from "@features/dashboard/useDashboardData";
import { formatTime } from "@shared/lib/date";
import { formatGrams, percentage } from "@shared/lib/number";
import { LoadingState } from "@shared/ui/State";

export function IndicadoresPage() {
  const { logs, reservatorio, isLoading } = useDashboardData();

  if (isLoading) {
    return <LoadingState />;
  }

  const weekLogs = getLastDaysLogs(logs, 7);
  const lastLog = getLastLog(logs);
  const average = weekLogs.length ? Math.round(sumGrams(weekLogs) / 7) : 0;

  return (
    <div className="grid gap-6">
      <header className="flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
        <div>
          <h1 className="text-3xl font-bold">Indicadores</h1>
          <p className="mt-1 text-neutral-600">Resumo de consumo para apoiar o acompanhamento da rotina alimentar.</p>
        </div>
      </header>

      <section className="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
        <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
          <Scale size={24} />
          <span className="text-neutral-600">Consumo em 7 dias</span>
          <strong className="text-3xl">{formatGrams(sumGrams(weekLogs))}</strong>
          <small className="text-neutral-500">Media diaria: {formatGrams(average)}</small>
        </article>
        <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
          <CalendarDays size={24} />
          <span className="text-neutral-600">Eventos em 7 dias</span>
          <strong className="text-3xl">{weekLogs.length}</strong>
          <small className="text-neutral-500">Alimentacoes registradas</small>
        </article>
        <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
          <BarChart3 size={24} />
          <span className="text-neutral-600">Ultima Liberacao</span>
          <strong className="text-3xl">{lastLog ? formatTime(lastLog.dataHora) : "--:--"}</strong>
          <small className="text-neutral-500">{lastLog ? formatGrams(lastLog.gramasLiberadas) : "Sem dados"}</small>
        </article>
        <article className="grid min-h-40 gap-2 rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">
          <Gauge size={24} />
          <span className="text-neutral-600">Status Reservatorio</span>
          <strong className="text-3xl">{percentage(reservatorio?.gramasRestantes || 0, reservatorio?.capacidadeTotal || 0)}%</strong>
          <small className="text-neutral-500">{formatGrams(reservatorio?.gramasRestantes || 0)} restantes</small>
        </article>
      </section>
    </div>
  );
}
