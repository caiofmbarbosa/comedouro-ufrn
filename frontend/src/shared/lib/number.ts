export function formatGrams(value = 0) {
  return `${new Intl.NumberFormat("pt-BR").format(value)} g`;
}

export function percentage(value: number, total: number) {
  if (!total) {
    return 0;
  }

  return Math.max(0, Math.min(100, Math.round((value / total) * 100)));
}
