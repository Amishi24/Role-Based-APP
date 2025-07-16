import { Component, OnInit } from '@angular/core';
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
export class ApexDashboardComponent implements OnInit {
  roles: string[] = [];
  users: DynamicUser[] = [];

  selectedRole: string = '';
  selectedUser: DynamicUser | null = null;

  user: DynamicUser | null = null;
  roleFunctionalities: string[] = [];

  userFunctionalities = new Set<string>();
  selectedFunctionalities = new Set<string>();

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.fetchAllRoles();
  }

  fetchAllRoles() {
    this.userService.getAllRoles().subscribe((roles) => {
      this.roles = roles;
    });
  }

  onRoleChange() {
    this.users = [];
    this.selectedUser = null;
    this.user = null;
    this.roleFunctionalities = [];
    this.userFunctionalities.clear();
    this.selectedFunctionalities.clear();

    this.userService.getUsersByRole(this.selectedRole).subscribe((users) => {
      this.users = users;
    });
  }

  onUsernameChange() {
    this.user = null;
    this.roleFunctionalities = [];
    this.userFunctionalities.clear();
    this.selectedFunctionalities.clear();
  }

  searchUser() {
    if (!this.selectedUser) return;

    this.user = this.selectedUser;
    this.loadFunctionalities(this.selectedUser);
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

  logout() {
    this.authService.logout();
  }
}
