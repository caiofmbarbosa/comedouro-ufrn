import { ButtonHTMLAttributes, ReactNode } from "react";

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  icon?: ReactNode;
  variant?: "primary" | "secondary" | "ghost" | "danger";
};

export function Button({ children, icon, variant = "primary", className = "", ...props }: ButtonProps) {
  const variants = {
    primary: "border-neutral-900 bg-neutral-900 text-white hover:bg-neutral-700",
    secondary: "border-neutral-300 bg-white text-neutral-950 hover:bg-neutral-100",
    ghost: "border-transparent bg-transparent text-neutral-950 hover:bg-neutral-100",
    danger: "border-red-700 bg-red-700 text-white hover:bg-red-800"
  };

  return (
    <button
      className={`inline-flex min-h-12 items-center justify-center gap-2 rounded-lg border px-5 font-bold transition disabled:cursor-not-allowed disabled:opacity-60 ${variants[variant]} ${className}`}
      {...props}
    >
      {icon}
      {children}
    </button>
  );
}
