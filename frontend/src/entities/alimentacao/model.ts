export type AlimentacaoLog = {
  id: string;
  gramasLiberadas: number;
  dataHora: string;
};

export type AlimentacaoPayload = {
  gramasLiberadas: number;
  dataHora: string;
};
