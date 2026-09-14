import { Component, input, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RoomSearch } from '../../models';
import { today } from '../../dates';

@Component({
  selector: 'app-search-form',
  imports: [FormsModule],
  templateUrl: './search-form.html',
  styleUrl: './search-form.scss',
})
export class SearchForm {
  readonly checkIn = input.required<string>();
  readonly checkOut = input.required<string>();
  readonly guests = input.required<number>();
  readonly pending = input(false);
  readonly search = output<RoomSearch>();

  protected readonly minDate = today();
  protected form: RoomSearch = {};

  protected submit(event: Event): void {
    event.preventDefault();
    this.search.emit({
      checkIn: this.form.checkIn ?? this.checkIn(),
      checkOut: this.form.checkOut ?? this.checkOut(),
      guests: this.form.guests ?? this.guests(),
    });
  }
}
