import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking, BookingRequest, Room, RoomSearch } from './models';

@Injectable({ providedIn: 'root' })
export class HotelApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = '/api';

  searchRooms(search: RoomSearch): Observable<Room[]> {
    let params = new HttpParams();
    if (search.checkIn) {
      params = params.set('checkIn', search.checkIn);
    }
    if (search.checkOut) {
      params = params.set('checkOut', search.checkOut);
    }
    if (search.guests) {
      params = params.set('guests', search.guests);
    }
    return this.http.get<Room[]>(`${this.baseUrl}/rooms`, { params });
  }

  getRoom(id: number): Observable<Room> {
    return this.http.get<Room>(`${this.baseUrl}/rooms/${id}`);
  }

  book(request: BookingRequest): Observable<Booking> {
    return this.http.post<Booking>(`${this.baseUrl}/bookings`, request);
  }

  bookingsByEmail(email: string): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.baseUrl}/bookings`, {
      params: new HttpParams().set('email', email),
    });
  }

  allBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.baseUrl}/bookings`);
  }

  cancel(reference: string): Observable<Booking> {
    return this.http.post<Booking>(`${this.baseUrl}/bookings/${reference}/cancel`, {});
  }
}
