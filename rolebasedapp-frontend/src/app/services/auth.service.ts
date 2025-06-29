import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/auth';
import { ChangePasswordRequest } from '../interfaces/change-password-request'; 
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  registerUser(userDetails: User) {
    return this.http.post(`${this.baseUrl}/register`, userDetails, { responseType: 'text' });
  }

  getUserByEmail(email: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users?email=${email}`);
  }
  
  loginUser(loginData: { email: string; password: string }): Observable<any> {
    return new Observable(observer => {
      this.http.post<{ token: string; email: string; role: string }>(
        `${this.baseUrl}/login`,
        loginData
      ).subscribe({
        next: (response) => {
          // Store token and role in localStorage
          localStorage.setItem('jwtToken', response.token);
          localStorage.setItem('userRole', response.role);
          localStorage.setItem('userEmail', response.email);
          
          observer.next(response);
          observer.complete();
        },
        error: (err) => {
          observer.error(err);
        }
      });
    });
  }

  changePassword(payload: ChangePasswordRequest) {
    return this.http.put('http://localhost:8080/api/auth/change-password', payload, {
      responseType: 'text'
    });

  }
}
