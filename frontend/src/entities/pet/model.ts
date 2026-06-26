export type Porte = "PEQUENO" | "MEDIO" | "GRANDE";
export type Sexo = "MASCULINO" | "FEMININO";

export type Pet = {
  id: string;
  name: string;
  raca: string;
  porte: Porte;
  peso: number;
  dataNascimento: string;
  sexo: Sexo;
};

export type PetPayload = Omit<Pet, "id">;
