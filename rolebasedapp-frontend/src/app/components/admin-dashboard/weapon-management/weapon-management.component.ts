import { Component } from '@angular/core';
import { WeaponService } from 'src/app/services/weapon.service';
import { Weapon } from 'src/app/interfaces/weapon';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-weapon-management',
  templateUrl: './weapon-management.component.html',
  styleUrls: ['./weapon-management.component.css']
})
export class WeaponManagementComponent {
  userEmail: string = '';
  category: string = '';
  availableWeapons: Weapon[] = [];
  assignedWeapons: string[] = [];
  userId: number = 0;

  constructor(
    private weaponService: WeaponService,
    private msgService: MessageService
  ) {}

  loadWeapons() {
    if (!this.category) {
      this.msgService.add({ severity: 'warn', summary: 'Category required', detail: 'Please select a category' });
      return;
    }
    if (!this.userEmail) {
      this.msgService.add({ severity: 'warn', summary: 'User Email required', detail: 'Please enter user email' });
      return;
    }

    this.weaponService.getUserIdByEmail(this.userEmail).subscribe(userId => {
      this.userId = userId;
      this.weaponService.getWeaponsByCategory(this.category).subscribe(data => {
        this.availableWeapons = data;
        this.fetchUserWeapons();
      });
    }, error => {
      this.msgService.add({ severity: 'error', summary: 'User not found', detail: 'Invalid user email' });
    });
  }

  fetchUserWeapons() {
    this.weaponService.getUserWeapons(this.userId).subscribe(data => {
    this.assignedWeapons = data.map(w => w.weaponId);    });
  }

  toggleWeapon(weaponId: string, checked: boolean) {
    if (checked) {
      this.weaponService.assignWeapon(this.userId, weaponId).subscribe(() => {
        this.msgService.add({ severity: 'success', summary: 'Assigned', detail: `Weapon ${weaponId} assigned` });
      });
    } else {
      this.weaponService.revokeWeapon(this.userId, weaponId).subscribe(() => {
        this.msgService.add({ severity: 'success', summary: 'Revoked', detail: `Weapon ${weaponId} revoked` });
      });
    }
  }
}