import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { UserService } from '../../../services/user.service';
import { DynamicUser } from '../../../interfaces/dynamic-user.model';

@Component({
  selector: 'app-role2-dashboard',
  templateUrl: './role2-dashboard.component.html',
  styleUrls: ['./role2-dashboard.component.css']
})
export class Role2DashboardComponent {
  
  currentUser = this.authService.getCurrentUser();
  user: DynamicUser | null = null;
  functionalities: string[] = [];

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    if (!this.currentUser) return;

    this.userService.getUserByUsername(this.currentUser.username).subscribe({
      next: user => {
        this.user = user;
        this.userService.getUserFunctionalities(user.userId).subscribe({
          next: funcs => this.functionalities = funcs,
          error: () => this.functionalities = []
        });
      },
      error: () => {
        this.functionalities = [];
      }
    });
  }
}