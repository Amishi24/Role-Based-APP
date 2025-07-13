import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApexDashboardComponent } from './apex-dashboard.component';

describe('ApexDashboardComponent', () => {
  let component: ApexDashboardComponent;
  let fixture: ComponentFixture<ApexDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApexDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApexDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
