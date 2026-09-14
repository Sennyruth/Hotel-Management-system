export interface Room {
  id: number;
  name: string;
  type: string;
  description: string;
  pricePerNight: number;
  capacity: number;
  imageUrl: string;
  amenities: string[];
  unitsAvailable: number;
  totalPrice: number | null;
  nights: number | null;
}

export interface RoomSearch {
  checkIn?: string;
  checkOut?: string;
  guests?: number;
}

export interface BookingRequest {
  roomId: number;
  guestName: string;
  guestEmail: string;
  guestPhone?: string;
  checkIn: string;
  checkOut: string;
  guests: number;
}

export interface Booking {
  id: number;
  reference: string;
  roomId: number;
  roomName: string;
  guestName: string;
  guestEmail: string;
  guestPhone: string | null;
  checkIn: string;
  checkOut: string;
  guests: number;
  totalPrice: number;
  status: 'CONFIRMED' | 'CANCELLED';
}
