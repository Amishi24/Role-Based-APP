import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { passwordMatchValidator } from 'src/app/shared/password-match.directive';
import { AuthService } from 'src/app/services/auth.service';
import { ChangePasswordRequest } from 'src/app/interfaces/change-password-request';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-change-password-form',
  templateUrl: './change-password-form.component.html',
  styleUrls: ['./change-password-form.component.css'],
  providers: [MessageService]
})
export class ChangePasswordFormComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private msg: MessageService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, { validators: passwordMatchValidator ('newPassword', 'confirmPassword') });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const email = sessionStorage.getItem('email');  // Assuming user is logged in
    if (!email) return;

    const payload: ChangePasswordRequest = {
      email,
      oldPassword: this.form.value.oldPassword,
      newPassword: this.form.value.newPassword
    };

    this.authService.changePassword(payload).subscribe({
      next: res => {
        this.msg.add({ severity: 'success', summary: 'Success', detail: res });
        this.form.reset();
      },
      error: err => {
        const detail = (typeof err.error === 'string') ? err.error : 'Something went wrong';
        this.msg.add({ severity: 'error', summary: 'Error', detail });
      }
    });
  }
}