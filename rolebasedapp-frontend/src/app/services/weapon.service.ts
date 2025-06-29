import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Weapon } from '../interfaces/weapon';

@Injectable({
  providedIn: 'root'
})
export class WeaponService {

  private adminBaseURL = 'http://localhost:8080/api/admin';
  private userBaseURL = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) { }

  // For admin dashboard
  getWeaponsByCategory(category: string): Observable<Weapon[]> {
    return this.http.get<Weapon[]>(`${this.adminBaseURL}/weapons?category=${category}`);
  }

  assignWeapon(userId: number, weaponId: string): Observable<string> {
    return this.http.post(`${this.adminBaseURL}/assign-weapon?userId=${userId}&weaponId=${weaponId}`, {}, { responseType: 'text' });
  }

  revokeWeapon(userId: number, weaponId: string): Observable<string> {
    return this.http.delete(`${this.adminBaseURL}/revoke-weapon?userId=${userId}&weaponId=${weaponId}`, { responseType: 'text' });
  }

  getUserWeapons(userId: number): Observable<Weapon[]> {
    return this.http.get<Weapon[]>(`${this.adminBaseURL}/user-weapons/${userId}`);
  }

  getUserIdByEmail(email: string): Observable<number> {
    return this.http.get<number>(`${this.adminBaseURL}/get-user-id?email=${email}`);
  }

  // For user dashboard
  getMyWeapons(): Observable<Weapon[]> {
    return this.http.get<Weapon[]>(`${this.userBaseURL}/my-weapons`);
  }
}