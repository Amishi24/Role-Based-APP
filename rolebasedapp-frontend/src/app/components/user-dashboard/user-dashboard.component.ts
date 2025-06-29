import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  activeSection: string = 'dashboard';

  constructor(private router: Router) {}

  ngOnInit(): void {}

  setSection(section: string) {
    this.activeSection = section;
  }

  logOut() {
    localStorage.clear();
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}
