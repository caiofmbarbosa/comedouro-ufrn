import { ReactNode } from "react";

type PanelProps = {
  children: ReactNode;
  className?: string;
};

export function Panel({ children, className = "" }: PanelProps) {
  return <section className={`rounded-lg border border-neutral-300 bg-white p-6 shadow-soft ${className}`}>{children}</section>;
}
