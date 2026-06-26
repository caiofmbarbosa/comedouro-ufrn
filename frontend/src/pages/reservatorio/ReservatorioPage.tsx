import { Gauge, RefreshCw } from "lucide-react";
import { useDashboardData } from "@features/dashboard/useDashboardData";
import { formatDate, formatTime } from "@shared/lib/date";
import { formatGrams, percentage } from "@shared/lib/number";
import { Button } from "@shared/ui/Button";
import { LoadingState } from "@shared/ui/State";

export function ReservatorioPage() {
  const { reservatorio, isLoading, refresh } = useDashboardData();

  if (isLoading) {
    return <LoadingState />;
  }

  const percent = percentage(reservatorio?.gramasRestantes || 0, reservatorio?.capacidadeTotal || 0);

  return (
    <div className="grid gap-6">
      <header className="flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
        <div>
          <h1 className="text-3xl font-bold">Reservatorio</h1>
          <p className="mt-1 text-neutral-600">Acompanhe a estimativa de racao restante apos cada alimentacao.</p>
        </div>
        <Button type="button" variant="secondary" icon={<RefreshCw size={18} />} onClick={refresh}>
          Atualizar
        </Button>
      </header>

      <section className="grid min-h-96 justify-items-center gap-5 rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
        <Gauge size={72} />
        <div className="h-9 w-full max-w-2xl overflow-hidden rounded-full border border-neutral-300 bg-neutral-100">
          <div className="h-full rounded-full bg-gradient-to-r from-emerald-700 to-emerald-400" style={{ width: `${percent}%` }} />
        </div>
        <strong className="text-4xl">{formatGrams(reservatorio?.gramasRestantes || 0)}</strong>
        <span className="text-neutral-600">{percent}% de {formatGrams(reservatorio?.capacidadeTotal || 0)}</span>
        {reservatorio && <small className="text-neutral-500">Ultima atualizacao em {formatDate(reservatorio.ultimaAtualizacao)} as {formatTime(reservatorio.ultimaAtualizacao)}</small>}
      </section>
    </div>
  );
}
