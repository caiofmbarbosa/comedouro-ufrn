import { FormEvent, useMemo, useState } from "react";
import { Filter, Plus, RotateCcw, Trash2 } from "lucide-react";
import { alimentacaoService } from "@features/alimentacao/alimentacaoService";
import { estimateReservoirByLog } from "@features/dashboard/metrics";
import { useDashboardData } from "@features/dashboard/useDashboardData";
import { formatDate, formatTime, toBackendLocalDateTime, toInputDate, toLocalDateTimeInput } from "@shared/lib/date";
import { formatGrams, percentage } from "@shared/lib/number";
import { Button } from "@shared/ui/Button";
import { Field, TextInput } from "@shared/ui/Field";
import { EmptyState, ErrorState, LoadingState } from "@shared/ui/State";
import { PetSelector } from "@widgets/PetSelector";

export function HistoricoPage() {
  const { pets, selectedPetId, setSelectedPetId, logs, reservatorio, isLoading, error, refresh } = useDashboardData();
  const [startDate, setStartDate] = useState(toInputDate(new Date(Date.now() - 6 * 24 * 60 * 60 * 1000)));
  const [endDate, setEndDate] = useState(toInputDate(new Date()));
  const [gramas, setGramas] = useState(120);
  const [dataHora, setDataHora] = useState(toLocalDateTimeInput());
  const [formError, setFormError] = useState("");

  const reservoirByLog = useMemo(() => estimateReservoirByLog(logs, reservatorio), [logs, reservatorio]);
  const filteredLogs = logs.filter((log) => {
    const day = log.dataHora.slice(0, 10);
    return day >= startDate && day <= endDate;
  });

  async function createLog(event: FormEvent) {
    event.preventDefault();
    if (!selectedPetId) {
      return;
    }

    setFormError("");
    try {
      await alimentacaoService.create(selectedPetId, {
        gramasLiberadas: gramas,
        dataHora: toBackendLocalDateTime(dataHora)
      });
      setDataHora(toLocalDateTimeInput());
      await refresh();
    } catch (err) {
      setFormError(err instanceof Error ? err.message : "Nao foi possivel registrar alimentacao.");
    }
  }

  async function removeLog(logId: string) {
    await alimentacaoService.remove(logId);
    await refresh();
  }

  if (isLoading) {
    return <LoadingState />;
  }

  return (
    <div className="grid gap-6">
      <header className="flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
        <div>
          <h1 className="text-3xl font-bold">Historico Alimentar</h1>
          <p className="mt-1 text-neutral-600">Consulte e registre eventos de alimentacao enviados ao backend.</p>
        </div>
        {pets.length > 0 && <PetSelector pets={pets} value={selectedPetId} onChange={setSelectedPetId} />}
      </header>

      {error && <ErrorState message={error} />}

      <section className="rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
        <div className="mb-5 flex items-center gap-2">
          <h2 className="flex items-center gap-2 text-xl font-bold"><Filter size={20} /> Filtros</h2>
        </div>
        <div className="grid items-end gap-4 md:grid-cols-3">
          <Field label="Data Inicial">
            <TextInput type="date" value={startDate} onChange={(event) => setStartDate(event.target.value)} />
          </Field>
          <Field label="Data Final">
            <TextInput type="date" value={endDate} onChange={(event) => setEndDate(event.target.value)} />
          </Field>
          <Button type="button" variant="secondary" icon={<RotateCcw size={18} />} onClick={() => {
            setStartDate(toInputDate(new Date(Date.now() - 6 * 24 * 60 * 60 * 1000)));
            setEndDate(toInputDate(new Date()));
          }}>
            Limpar
          </Button>
        </div>
      </section>

      <section className="rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
        <div className="mb-5 flex items-center gap-2">
          <h2 className="text-xl font-bold">Registrar Alimentacao</h2>
        </div>
        <form className="grid items-end gap-4 md:grid-cols-3" onSubmit={createLog}>
          <Field label="Gramas liberadas">
            <TextInput type="number" min="1" value={gramas} onChange={(event) => setGramas(Number(event.target.value))} required />
          </Field>
          <Field label="Data e hora">
            <TextInput type="datetime-local" value={dataHora} onChange={(event) => setDataHora(event.target.value)} required />
          </Field>
          <Button type="submit" icon={<Plus size={18} />} disabled={!selectedPetId}>
            Registrar
          </Button>
        </form>
        {formError && <ErrorState message={formError} />}
      </section>

      <section className="overflow-x-auto rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
        {filteredLogs.length === 0 ? (
          <EmptyState message="Nenhum registro encontrado para o periodo selecionado." />
        ) : (
          <table className="w-full min-w-215 border-collapse">
            <thead>
              <tr>
                <th className="border-b border-neutral-300 bg-neutral-100 p-4 text-left">Data</th>
                <th className="border-b border-neutral-300 bg-neutral-100 p-4 text-left">Horario</th>
                <th className="border-b border-neutral-300 bg-neutral-100 p-4 text-left">Gramas Liberadas</th>
                <th className="border-b border-neutral-300 bg-neutral-100 p-4 text-left">Reservatorio Estimado</th>
                <th className="border-b border-neutral-300 bg-neutral-100 p-4 text-left">Tipo de Evento</th>
                <th className="border-b border-neutral-300 bg-neutral-100 p-4 text-left" />
              </tr>
            </thead>
            <tbody>
              {filteredLogs.map((log) => {
                const remaining = reservoirByLog.get(log.id) || 0;
                return (
                  <tr key={log.id}>
                    <td className="border-b border-neutral-200 p-4">{formatDate(log.dataHora)}</td>
                    <td className="border-b border-neutral-200 p-4">{formatTime(log.dataHora)}</td>
                    <td className="border-b border-neutral-200 p-4">{formatGrams(log.gramasLiberadas)}</td>
                    <td className="border-b border-neutral-200 p-4">{formatGrams(remaining)} ({percentage(remaining, reservatorio?.capacidadeTotal || 0)}%)</td>
                    <td className="border-b border-neutral-200 p-4">Liberacao Programada</td>
                    <td className="border-b border-neutral-200 p-4">
                      <button className="grid h-10 w-10 place-items-center rounded-lg text-red-700 hover:bg-red-50" onClick={() => removeLog(log.id)} aria-label="Excluir registro">
                        <Trash2 size={18} />
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
      </section>
    </div>
  );
}
