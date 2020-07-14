import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KudoModalComponent } from './kudo-modal.component';

describe('KudoModalComponent', () => {
  let component: KudoModalComponent;
  let fixture: ComponentFixture<KudoModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KudoModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KudoModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
