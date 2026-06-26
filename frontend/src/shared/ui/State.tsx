export function LoadingState() {
  return <div className="rounded-lg border border-neutral-300 bg-white p-5 shadow-soft">Carregando dados...</div>;
}

export function EmptyState({ message }: { message: string }) {
  return <div className="rounded-lg border border-neutral-300 bg-white p-5 text-neutral-500 shadow-soft">{message}</div>;
}

export function ErrorState({ message }: { message: string }) {
  return <div className="rounded-lg border border-red-200 bg-red-50 p-5 text-red-700 shadow-soft">{message}</div>;
}
