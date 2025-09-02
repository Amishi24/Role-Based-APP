import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Role1DashboardComponent } from './role1-dashboard.component';

describe('Role1DashboardComponent', () => {
  let component: Role1DashboardComponent;
  let fixture: ComponentFixture<Role1DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Role1DashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Role1DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
