import { FormEvent, useState } from "react";
import { Eye, Lock, Mail, PawPrint, User } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@features/auth/AuthProvider";
import { Button } from "@shared/ui/Button";
import { Field, TextInput } from "@shared/ui/Field";

export function LoginPage() {
  const navigate = useNavigate();
  const { login, register } = useAuth();
  const [isRegistering, setIsRegistering] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [form, setForm] = useState({
    nome: "",
    email: "",
    password: "",
    confirmPassword: ""
  });

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setError("");
    setIsSubmitting(true);

    try {
      if (isRegistering) {
        await register(form);
      } else {
        await login({ email: form.email, password: form.password });
      }
      navigate("/");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Nao foi possivel autenticar.");
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <main className="grid min-h-screen grid-rows-[auto_1fr_auto] gap-7 bg-[#f6f7f6] p-5 md:p-7">
      <header className="flex items-center gap-4 text-2xl font-bold md:text-3xl">
        <PawPrint size={40} />
        <strong>Sistema de Alimentacao de Caes</strong>
      </header>

      <section className="grid items-center">
        <section className="mx-auto grid w-full max-w-6xl overflow-hidden rounded-lg border border-neutral-300 bg-white shadow-soft lg:grid-cols-2">
          <div className="flex min-h-90 flex-col items-center justify-center border-b border-neutral-300 p-8 text-center lg:min-h-155 lg:border-b-0 lg:border-r lg:p-12">
            <PawPrint size={112} />
            <h1 className="mt-6 max-w-md text-3xl font-bold md:text-4xl">Sistema de Alimentacao de Caes</h1>
            <p className="mt-4 text-lg text-neutral-600 md:text-xl">Acompanhe de forma inteligente a alimentacao do seu cao.</p>
          </div>

          <form className="flex flex-col justify-center gap-5 p-8 lg:p-12" onSubmit={handleSubmit}>
            <h2 className="mb-2 text-3xl font-bold md:text-4xl">{isRegistering ? "Criar conta" : "Login"}</h2>

            {isRegistering && (
              <Field label="Nome">
                <div className="flex min-h-14 items-center gap-3 rounded-lg border border-neutral-300 bg-white px-3">
                  <User size={20} />
                  <TextInput
                    className="border-0 px-0 focus:border-0"
                    value={form.nome}
                    onChange={(event) => setForm((current) => ({ ...current, nome: event.target.value }))}
                    placeholder="Digite seu nome"
                    required
                  />
                </div>
              </Field>
            )}

            <Field label="E-mail">
              <div className="flex min-h-14 items-center gap-3 rounded-lg border border-neutral-300 bg-white px-3">
                <Mail size={20} />
                <TextInput
                  className="border-0 px-0 focus:border-0"
                  type="email"
                  value={form.email}
                  onChange={(event) => setForm((current) => ({ ...current, email: event.target.value }))}
                  placeholder="Digite seu e-mail"
                  required
                />
              </div>
            </Field>

            <Field label="Senha">
              <div className="flex min-h-14 items-center gap-3 rounded-lg border border-neutral-300 bg-white px-3">
                <Lock size={20} />
                <TextInput
                  className="border-0 px-0 focus:border-0"
                  type={showPassword ? "text" : "password"}
                  value={form.password}
                  onChange={(event) => setForm((current) => ({ ...current, password: event.target.value }))}
                  placeholder="Digite sua senha"
                  required
                  minLength={6}
                />
                <button type="button" className="grid h-10 w-10 place-items-center rounded-lg hover:bg-neutral-100" onClick={() => setShowPassword((value) => !value)} aria-label="Mostrar senha">
                  <Eye size={22} />
                </button>
              </div>
            </Field>

            {isRegistering && (
              <Field label="Confirmar senha">
                <div className="flex min-h-14 items-center gap-3 rounded-lg border border-neutral-300 bg-white px-3">
                  <Lock size={20} />
                  <TextInput
                    className="border-0 px-0 focus:border-0"
                    type={showPassword ? "text" : "password"}
                    value={form.confirmPassword}
                    onChange={(event) => setForm((current) => ({ ...current, confirmPassword: event.target.value }))}
                    placeholder="Confirme sua senha"
                    required
                    minLength={6}
                  />
                </div>
              </Field>
            )}

            {error && <p className="text-red-700">{error}</p>}

            <Button type="submit" disabled={isSubmitting}>
              {isSubmitting ? "Aguarde..." : isRegistering ? "Cadastrar" : "Entrar"}
            </Button>

            <button type="button" className="border-0 bg-transparent text-neutral-950 underline" onClick={() => setIsRegistering((value) => !value)}>
              {isRegistering ? "Ja tenho conta" : "Criar uma conta"}
            </button>
          </form>
        </section>
      </section>

      <footer className="mx-auto w-full max-w-6xl rounded-lg border border-neutral-300 bg-white p-5 text-center shadow-soft">
        Seguranca e Privacidade: seus dados nao sao compartilhados com outros tutores.
      </footer>
    </main>
  );
}
