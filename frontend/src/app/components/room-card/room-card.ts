import { Component, input } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Room } from '../../models';

@Component({
  selector: 'app-room-card',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './room-card.html',
  styleUrl: './room-card.scss',
})
export class RoomCard {
  readonly room = input.required<Room>();
  readonly checkIn = input<string>('');
  readonly checkOut = input<string>('');
  readonly guests = input<number>(2);
}
