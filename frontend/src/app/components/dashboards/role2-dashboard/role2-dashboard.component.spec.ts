import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Role2DashboardComponent } from './role2-dashboard.component';

describe('Role2DashboardComponent', () => {
  let component: Role2DashboardComponent;
  let fixture: ComponentFixture<Role2DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Role2DashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Role2DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
