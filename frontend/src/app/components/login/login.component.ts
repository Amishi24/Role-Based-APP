import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'] // Optional, only if CSS file exists
})
export class LoginComponent {
  username = '';
  role = '';
  errorMessage = '';

  roles = ['apex', 'role1', 'role2'];  // Example roles, adjust as needed

  constructor(private authService: AuthService) {}

  login(): void {
    const success = this.authService.login(this.username, this.role);
    if (!success) {
      this.errorMessage = 'Invalid username or role!';
    }
  }
}