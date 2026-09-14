import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelApi } from '../../hotel-api';
import { Room, RoomSearch } from '../../models';
import { apiErrorMessage, daysFromNow } from '../../dates';
import { RoomCard } from '../../components/room-card/room-card';
import { SearchForm } from '../../components/search-form/search-form';

@Component({
  selector: 'app-rooms',
  imports: [RoomCard, SearchForm],
  templateUrl: './rooms.html',
  styleUrl: './rooms.scss',
})
export class Rooms {
  private readonly api = inject(HotelApi);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  protected readonly rooms = signal<Room[]>([]);
  protected readonly loading = signal(false);
  protected readonly error = signal('');
  protected readonly checkIn = signal(daysFromNow(7));
  protected readonly checkOut = signal(daysFromNow(9));
  protected readonly guests = signal(2);

  constructor() {
    this.route.queryParamMap.subscribe((params) => {
      this.checkIn.set(params.get('checkIn') || this.checkIn());
      this.checkOut.set(params.get('checkOut') || this.checkOut());
      this.guests.set(Number(params.get('guests')) || this.guests());
      this.load();
    });
  }

  protected onSearch(search: RoomSearch): void {
    this.router.navigate(['/rooms'], { queryParams: search });
  }

  private load(): void {
    this.loading.set(true);
    this.error.set('');
    this.api
      .searchRooms({ checkIn: this.checkIn(), checkOut: this.checkOut(), guests: this.guests() })
      .subscribe({
        next: (rooms) => {
          this.rooms.set(rooms);
          this.loading.set(false);
        },
        error: (error) => {
          this.error.set(apiErrorMessage(error, 'Could not load availability. Is the API running?'));
          this.rooms.set([]);
          this.loading.set(false);
        },
      });
  }
}
