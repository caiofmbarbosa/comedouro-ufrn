import { FormEvent, useEffect, useState } from "react";
import { Dog, Pencil, Plus, Trash2 } from "lucide-react";
import { Pet, PetPayload, Porte, Sexo } from "@entities/pet/model";
import { petService } from "@features/pets/petService";
import { Button } from "@shared/ui/Button";
import { Field, SelectInput, TextInput } from "@shared/ui/Field";
import { EmptyState, ErrorState, LoadingState } from "@shared/ui/State";

const emptyForm: PetPayload = {
  name: "",
  raca: "",
  porte: "MEDIO",
  peso: 0,
  dataNascimento: "",
  sexo: "MASCULINO"
};

export function PetsPage() {
  const [pets, setPets] = useState<Pet[]>([]);
  const [form, setForm] = useState<PetPayload>(emptyForm);
  const [editingPetId, setEditingPetId] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  async function loadPets() {
    setIsLoading(true);
    setError("");
    try {
      setPets(await petService.list());
    } catch (err) {
      setError(err instanceof Error ? err.message : "Nao foi possivel carregar os pets.");
    } finally {
      setIsLoading(false);
    }
  }

  useEffect(() => {
    loadPets();
  }, []);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setError("");
    try {
      if (editingPetId) {
        await petService.update(editingPetId, form);
      } else {
        await petService.create(form);
      }
      setForm(emptyForm);
      setEditingPetId(null);
      await loadPets();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Nao foi possivel salvar o pet.");
    }
  }

  function startEdit(pet: Pet) {
    setEditingPetId(pet.id);
    setForm({
      name: pet.name,
      raca: pet.raca,
      porte: pet.porte,
      peso: pet.peso,
      dataNascimento: pet.dataNascimento,
      sexo: pet.sexo
    });
  }

  async function removePet(petId: string) {
    if (!confirm("Excluir este pet?")) {
      return;
    }

    await petService.remove(petId);
    await loadPets();
  }

  return (
    <div className="grid gap-6">
      <header className="flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
        <div>
          <h1 className="text-3xl font-bold">Gerenciamento de Pets</h1>
          <p className="mt-1 text-neutral-600">Cadastre, atualize e vincule pets ao tutor autenticado.</p>
        </div>
      </header>

      <div className="grid items-start gap-6 xl:grid-cols-[430px_minmax(0,1fr)]">
        <section className="rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
          <h2 className="mb-5 text-xl font-bold">{editingPetId ? "Atualizar pet" : "Cadastrar pet"}</h2>
          <form className="grid gap-4 md:grid-cols-2" onSubmit={handleSubmit}>
            <Field label="Nome">
              <TextInput value={form.name} onChange={(event) => setForm((current) => ({ ...current, name: event.target.value }))} required />
            </Field>
            <Field label="Raca">
              <TextInput value={form.raca} onChange={(event) => setForm((current) => ({ ...current, raca: event.target.value }))} required />
            </Field>
            <Field label="Porte">
              <SelectInput value={form.porte} onChange={(event) => setForm((current) => ({ ...current, porte: event.target.value as Porte }))}>
                <option value="PEQUENO">Pequeno</option>
                <option value="MEDIO">Medio</option>
                <option value="GRANDE">Grande</option>
              </SelectInput>
            </Field>
            <Field label="Peso (kg)">
              <TextInput
                type="number"
                min="0.1"
                step="0.1"
                value={form.peso || ""}
                onChange={(event) => setForm((current) => ({ ...current, peso: Number(event.target.value) }))}
                required
              />
            </Field>
            <Field label="Nascimento">
              <TextInput
                type="date"
                value={form.dataNascimento}
                onChange={(event) => setForm((current) => ({ ...current, dataNascimento: event.target.value }))}
                required
              />
            </Field>
            <Field label="Sexo">
              <SelectInput value={form.sexo} onChange={(event) => setForm((current) => ({ ...current, sexo: event.target.value as Sexo }))}>
                <option value="MASCULINO">Masculino</option>
                <option value="FEMININO">Feminino</option>
              </SelectInput>
            </Field>
            <div className="col-span-full flex gap-3">
              <Button type="submit" icon={editingPetId ? <Pencil size={18} /> : <Plus size={18} />}>
                {editingPetId ? "Atualizar" : "Cadastrar"}
              </Button>
              {editingPetId && (
                <Button type="button" variant="secondary" onClick={() => { setEditingPetId(null); setForm(emptyForm); }}>
                  Cancelar
                </Button>
              )}
            </div>
          </form>
          {error && <ErrorState message={error} />}
        </section>

        <section className="rounded-lg border border-neutral-300 bg-white p-6 shadow-soft">
          <h2 className="mb-5 text-xl font-bold">Pets cadastrados</h2>
          {isLoading && <LoadingState />}
          {!isLoading && pets.length === 0 && <EmptyState message="Nenhum pet cadastrado ainda." />}
          <div className="grid gap-3">
            {pets.map((pet) => (
              <article className="grid grid-cols-[auto_minmax(0,1fr)_auto] items-center gap-4 rounded-lg border border-neutral-300 bg-neutral-100 p-4" key={pet.id}>
                <Dog size={28} />
                <div>
                  <strong className="block">{pet.name}</strong>
                  <span className="block text-neutral-600">{pet.raca} - {pet.porte.toLowerCase()} - {pet.peso} kg</span>
                </div>
                <div className="flex gap-1">
                  <button className="grid h-10 w-10 place-items-center rounded-lg hover:bg-neutral-200" onClick={() => startEdit(pet)} aria-label="Editar pet">
                    <Pencil size={18} />
                  </button>
                  <button className="grid h-10 w-10 place-items-center rounded-lg text-red-700 hover:bg-red-50" onClick={() => removePet(pet.id)} aria-label="Excluir pet">
                    <Trash2 size={18} />
                  </button>
                </div>
              </article>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
