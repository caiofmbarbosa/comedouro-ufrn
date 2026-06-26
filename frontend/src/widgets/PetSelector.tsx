import { Pet } from "@entities/pet/model";

type PetSelectorProps = {
  pets: Pet[];
  value: string;
  onChange: (petId: string) => void;
};

export function PetSelector({ pets, value, onChange }: PetSelectorProps) {
  return (
    <select
      className="min-h-12 w-full rounded-lg border border-neutral-300 bg-white px-4 text-neutral-950 shadow-sm outline-none md:max-w-56"
      value={value}
      onChange={(event) => onChange(event.target.value)}
    >
      {pets.map((pet) => (
        <option key={pet.id} value={pet.id}>
          {pet.name}
        </option>
      ))}
    </select>
  );
}
