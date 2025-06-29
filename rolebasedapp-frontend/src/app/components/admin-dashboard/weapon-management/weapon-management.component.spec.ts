import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeaponManagementComponent } from './weapon-management.component';

describe('WeaponManagementComponent', () => {
  let component: WeaponManagementComponent;
  let fixture: ComponentFixture<WeaponManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WeaponManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WeaponManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
