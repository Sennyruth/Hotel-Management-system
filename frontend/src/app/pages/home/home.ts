import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HotelApi } from '../../hotel-api';
import { Room, RoomSearch } from '../../models';
import { daysFromNow } from '../../dates';
import { RoomCard } from '../../components/room-card/room-card';
import { SearchForm } from '../../components/search-form/search-form';

@Component({
  selector: 'app-home',
  imports: [RouterLink, RoomCard, SearchForm],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  private readonly api = inject(HotelApi);
  private readonly router = inject(Router);

  protected readonly checkIn = daysFromNow(7);
  protected readonly checkOut = daysFromNow(9);
  protected readonly guests = 2;
  protected readonly rooms = signal<Room[]>([]);

  protected readonly highlights = [
    { title: 'Seafront infinity pool', text: 'Swim into the sunset from our heated rooftop pool.' },
    { title: 'Farm-to-table dining', text: 'Coastal cuisine from our kitchen garden, served all day.' },
    { title: 'Spa & wellness', text: 'Steam rooms, deep-tissue massages and a 24-hour gym.' },
  ];

  constructor() {
    this.api.searchRooms({ guests: 1 }).subscribe((rooms) => this.rooms.set(rooms.slice(0, 3)));
  }

  protected onSearch(search: RoomSearch): void {
    this.router.navigate(['/rooms'], { queryParams: search });
  }
}
