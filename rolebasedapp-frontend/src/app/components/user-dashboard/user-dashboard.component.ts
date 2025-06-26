import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent {

  constructor(private router: Router) {}

  logOut() {
    sessionStorage.clear(); // Ya sessionStorage.removeItem('email'); sessionStorage.removeItem('role');
    this.router.navigate(['/login']);
  }

}
