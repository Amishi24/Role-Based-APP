import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.css']
})
export class SidebarMenuComponent implements OnInit {
  currentUser = this.authService.getCurrentUser();
  functionalities: string[] = [];

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Apex doesn’t need to show its own menu
    if (!this.currentUser || this.currentUser.role === 'apex') return;

    this.userService.getUserByUsername(this.currentUser.username).subscribe(user => {
      this.userService.getUserFunctionalities(user.userId).subscribe(funcs => {
        this.functionalities = funcs;
      });
    });
  }

  logout(): void {
    this.authService.logout();
  }
}