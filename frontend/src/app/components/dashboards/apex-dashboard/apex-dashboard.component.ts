import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { AuthService } from '../../../services/auth.service';
import { DynamicUser } from '../../../interfaces/dynamic-user.model';
import { AssignFunctionalityRequest } from '../../../interfaces/assign-functionality-request.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-apex-dashboard',
  templateUrl: './apex-dashboard.component.html',
  styleUrls: ['./apex-dashboard.component.css'],
})
export class ApexDashboardComponent {
  username = '';
  user: DynamicUser | null = null;
  roleFunctionalities: string[] = [];

  userFunctionalities = new Set<string>();      // from DB
  selectedFunctionalities = new Set<string>();  // UI state

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private messageService: MessageService
  ) {}

  logout() {
    this.authService.logout();
  }

  searchUser() {
    this.userService.getUserByUsername(this.username).subscribe(user => {
      this.user = user;
      this.loadFunctionalities(user);
    });
  }

  loadFunctionalities(user: DynamicUser) {
    this.userService.getFunctionalitiesByRole(user.userRole).subscribe(roleFuncs => {
      this.roleFunctionalities = roleFuncs;

      this.userService.getUserFunctionalities(user.userId).subscribe(userFuncs => {
        this.userFunctionalities = new Set(userFuncs);
        this.selectedFunctionalities = new Set(userFuncs);
      });
    });
  }

  isChecked(func: string): boolean {
    return this.selectedFunctionalities.has(func);
  }

  onToggle(func: string, event: any) {
    event.checked
      ? this.selectedFunctionalities.add(func)
      : this.selectedFunctionalities.delete(func);
  }

  saveChanges() {
    if (!this.user) return;

    const toAssign = [...this.selectedFunctionalities].filter(f => !this.userFunctionalities.has(f));
    const toRevoke = [...this.userFunctionalities].filter(f => !this.selectedFunctionalities.has(f));

    if (toAssign.length > 0) {
      const assignRequests: AssignFunctionalityRequest[] = toAssign.map(f => ({
        userId: this.user!.userId,
        functionality: f
      }));
      this.userService.assignFunctionalities(assignRequests).subscribe();
    }

    toRevoke.forEach(f => {
      this.userService.revokeFunctionality(this.user!.userId, f).subscribe();
    });

    this.userFunctionalities = new Set(this.selectedFunctionalities);

    this.messageService.add({
      severity: 'success',
      summary: 'Saved',
      detail: 'Changes applied to database'
    });
  }
}