export function toIsoDate(date: Date): string {
  return date.toISOString().slice(0, 10);
}

export function today(): string {
  return toIsoDate(new Date());
}

export function daysFromNow(days: number): string {
  const date = new Date();
  date.setDate(date.getDate() + days);
  return toIsoDate(date);
}

export function nightsBetween(checkIn: string, checkOut: string): number {
  const start = new Date(checkIn).getTime();
  const end = new Date(checkOut).getTime();
  return Math.max(0, Math.round((end - start) / 86_400_000));
}

export function apiErrorMessage(error: unknown, fallback: string): string {
  const message = (error as { error?: { message?: string } })?.error?.message;
  return message ?? fallback;
}
