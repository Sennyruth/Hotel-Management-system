import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { App } from './app';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideRouter([])],
    }).compileComponents();
  });

  it('renders the hotel brand and navigation', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const element = fixture.nativeElement as HTMLElement;
    expect(element.querySelector('.brand-text strong')?.textContent).toContain('Azure Bay');
    expect(element.querySelectorAll('nav a').length).toBe(4);
  });
});
