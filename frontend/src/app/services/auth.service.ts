import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUser: { username: string; role: string } | null = null;

  private readonly validUsers = [
    { username: 'apex1', role: 'apex' },
    { username: 'Amishi', role: 'role1' },
    { username: 'Harshita', role: 'role1' },
    { username: 'Jiya', role: 'role2' },
  ];

  constructor(private router: Router) {}

  login(username: string, role: string): boolean {
    const user = this.validUsers.find(u => u.username === username && u.role === role);
    if (user) {
      this.currentUser = user;
      this.router.navigate([`/${role}`]);
      return true;
    }
    return false;
  }

  logout(): void {
    this.currentUser = null;
    this.router.navigate(['/']);
  }

  getCurrentUser() {
    return this.currentUser;
  }
}