import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KudoCardComponent } from './kudo-card.component';

describe('KudoCardComponent', () => {
  let component: KudoCardComponent;
  let fixture: ComponentFixture<KudoCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KudoCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KudoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
