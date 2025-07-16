import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DynamicUser } from '../interfaces/dynamic-user.model';
import { AssignFunctionalityRequest } from '../interfaces/assign-functionality-request.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  private readonly baseUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) {}

  getUserByUsername(username: string): Observable<DynamicUser> {
    return this.http.get<DynamicUser>(`${this.baseUrl}/details/${username}`);
  }

  getFunctionalitiesByRole(roleName: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/role/${roleName}/functionalities`);
  }

  getUserFunctionalities(userId: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/${userId}/functionalities`);
  }

  assignFunctionalities(payload: AssignFunctionalityRequest[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/assign-functionalities`, payload, {
      responseType: 'text'
    });
  }

  revokeFunctionality(userId: number, functionality: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${userId}/revoke/${functionality}`, {
      responseType: 'text'
    });
  }

  // Get all distinct roles
  getAllRoles(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/roles`);
  }

  // Get users by role
  getUsersByRole(role: string): Observable<DynamicUser[]> {
    return this.http.get<DynamicUser[]>(`${this.baseUrl}/role/${role}/users`);
  }
}
