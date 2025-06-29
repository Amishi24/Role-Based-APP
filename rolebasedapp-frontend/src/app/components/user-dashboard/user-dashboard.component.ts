import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WeaponService } from 'src/app/services/weapon.service';
import { Weapon } from 'src/app/interfaces/weapon';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  activeSection: string = 'dashboard';
  categoryWeapons: Weapon[] = [];
  userId: number = 0;

  constructor(private router: Router, private weaponService: WeaponService) {}

  ngOnInit(): void {
    // Get userId from token or localStorage/sessionStorage
    const userIdStored = sessionStorage.getItem('userId') || localStorage.getItem('userId');
    if (userIdStored) {
      this.userId = +userIdStored;
    }
  }

  setSection(section: string) {
    this.activeSection = section;
    if (section === 'army' || section === 'navy' || section === 'airforce') {
      this.loadCategoryWeapons(section);
    }
  }

  loadCategoryWeapons(category: string) {
    this.weaponService.getMyWeapons().subscribe(data => {
      this.categoryWeapons = data.filter(w =>
        w.category.toLowerCase() === category.toLowerCase()
      );
    });
  }

  logOut() {
    localStorage.clear();
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}