import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  loginError: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private msgService: MessageService
  ) { }

  get email() {
    return this.loginForm.controls['email'];
  }
  get password() { return this.loginForm.controls['password']; }

  loginUser() {
    if (this.loginForm.invalid) return;
    const { email, password } = this.loginForm.value;
    if (!email || !password) return;
    this.loginError = '';

    this.authService.loginUser({ email, password }).subscribe(
      (response: any) => {
        //  Save necessary data
        localStorage.setItem('jwtToken', response.token);
        sessionStorage.setItem('email', response.email);  
        sessionStorage.setItem('role', response.role);

        this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Login successful' });

        //  Redirect based on role
        const role = response.role?.toUpperCase();
        if (role === 'ADMIN') {
          this.router.navigate(['/admin-dashboard']);
        } else if (role === 'USER') {
          this.router.navigate(['/user-dashboard']);
        }
      },
      error => {
        let errorMsg = 'Something went wrong';
        if (error.status === 404 && error.error === 'User does not exist') {
          errorMsg = 'User does not exist';
        } else if (error.status === 401 && error.error === 'Incorrect password') {
          errorMsg = 'Incorrect password';
        } else if (error.status === 403 && error.error === 'Not approved yet') {
          errorMsg = 'Not approved yet';
        }
        this.msgService.add({ severity: 'error', summary: 'Error', detail: errorMsg });
      }
    );
  }
}
