import { InputHTMLAttributes, ReactNode, SelectHTMLAttributes } from "react";

type FieldProps = {
  label: string;
  children: ReactNode;
};

export function Field({ label, children }: FieldProps) {
  return (
    <label className="grid gap-2 font-semibold">
      <span>{label}</span>
      {children}
    </label>
  );
}

export function TextInput({ className = "", ...props }: InputHTMLAttributes<HTMLInputElement>) {
  return <input className={`min-h-12 w-full rounded-lg border border-neutral-300 bg-white px-3 text-neutral-950 outline-none transition focus:border-neutral-700 ${className}`} {...props} />;
}

export function SelectInput({ className = "", ...props }: SelectHTMLAttributes<HTMLSelectElement>) {
  return <select className={`min-h-12 w-full rounded-lg border border-neutral-300 bg-white px-3 text-neutral-950 outline-none transition focus:border-neutral-700 ${className}`} {...props} />;
}
