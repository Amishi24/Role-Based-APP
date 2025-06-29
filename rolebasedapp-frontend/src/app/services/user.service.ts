import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';
import { CreateUserRequest } from '../interfaces/create-user-request';
import { UpdateUserRequest } from '../interfaces/update-user-request';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) { }

  getUsersList(): Observable<User[]> {
    return this.httpClient.get<User[]>('http://localhost:8080/api/admin/users');
  }

  createUser(user: CreateUserRequest): Observable<string> {
    return this.httpClient.post('http://localhost:8080/api/admin/add-user', user, { responseType: 'text' });
  }

  getUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(`http://localhost:8080/api/admin/user/${id}`);
  }

  updateUserById(id: number, user: UpdateUserRequest): Observable<string> {
    return this.httpClient.put(`http://localhost:8080/api/admin/user/${id}`, user, { responseType: 'text' });
  }

  approveUser(id: number): Observable<string> {
    return this.httpClient.put(`http://localhost:8080/api/admin/approve/${id}`, {}, { responseType: 'text' });
  }

  rejectUser(id: number): Observable<string> {
    return this.httpClient.delete(`http://localhost:8080/api/admin/reject/${id}`, { responseType: 'text' });
  }

  deleteUser(id: number): Observable<string> {
    return this.httpClient.delete(`http://localhost:8080/api/admin/delete/${id}`, { responseType: 'text' });
  }
}
