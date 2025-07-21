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
  // Tab 1 state
  roles: string[] = [];
  users: DynamicUser[] = [];
  selectedRole: string = '';
  selectedUser: DynamicUser | null = null;
  user: DynamicUser | null = null;
  roleFunctionalities: string[] = [];
  userFunctionalities = new Set<string>();
  selectedFunctionalities = new Set<string>();

  // Tab 2 state
  newRoleName: string = '';
  newFunctionalities: string[] = [''];
  newUsername: string = '';

  // Table data
  userRoleFunctionalityData: {
    roleName: string;
    userName: string;
    functionality: string;
  }[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.fetchAllRoles();
    this.loadTableData();
  }

  // -------- Tab 1 Methods --------
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
    this.userService.getFunctionalitiesByRole(user.userRole).subscribe((roleFuncs) => {
      this.roleFunctionalities = roleFuncs;

      this.userService.getUserFunctionalities(user.userId).subscribe((userFuncs) => {
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

    const toAssign = [...this.selectedFunctionalities].filter(
      (f) => !this.userFunctionalities.has(f)
    );
    const toRevoke = [...this.userFunctionalities].filter(
      (f) => !this.selectedFunctionalities.has(f)
    );

    if (toAssign.length > 0) {
      const assignRequests: AssignFunctionalityRequest[] = toAssign.map((f) => ({
        userId: this.user!.userId,
        functionality: f,
      }));
      this.userService.assignFunctionalities(assignRequests).subscribe();
    }

    toRevoke.forEach((f) => {
      this.userService.revokeFunctionality(this.user!.userId, f).subscribe();
    });

    this.userFunctionalities = new Set(this.selectedFunctionalities);

    this.messageService.add({
      severity: 'success',
      summary: 'Saved',
      detail: 'Changes applied to database',
    });

    // Update table data after save
    const updatedRows = [...this.selectedFunctionalities].map((f) => ({
      roleName: this.user!.userRole,
      userName: this.user!.userName,
      functionality: f,
    }));

    this.userRoleFunctionalityData = this.userRoleFunctionalityData.filter(
      (row) =>
        row.roleName !== this.user!.userRole || row.userName !== this.user!.userName
    );

    this.userRoleFunctionalityData.push(...updatedRows);
  }

  // -------- Tab 2 Methods --------

  addFunctionalityField() {
    this.newFunctionalities.push('');
  }

  removeFunctionalityField(index: number) {
    if (this.newFunctionalities.length > 1) {
      this.newFunctionalities.splice(index, 1);
    }
  }

  submitNewData() {
    const payload = {
      roleName: this.newRoleName.trim(),
      functionalities: this.newFunctionalities.filter((f) => f.trim() !== ''),
      username: this.newUsername.trim(),
    };

    const isCreatingNewRole = !this.roles.includes(payload.roleName);

    if (!payload.roleName) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Validation Error',
        detail: 'Role name is required.',
      });
      return;
    }

    if (isCreatingNewRole && payload.functionalities.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Validation Error',
        detail: 'At least one functionality is required for a new role.',
      });
      return;
    }

    this.userService.createRoleUserAndFunctionalities(payload).subscribe({
      next: (res) => {
        const msg = res.toLowerCase();

        if (msg.includes('new role')) {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'New role, functionalities, and user created',
          });
        } else if (msg.includes('user added to existing role')) {
          this.messageService.add({
            severity: 'success',
            summary: 'User Created',
            detail: 'New user added to existing role',
          });
        } else if (msg.includes('functionalities updated')) {
          this.messageService.add({
            severity: 'info',
            summary: 'Updated',
            detail: 'Role functionalities updated',
          });
        } else {
          this.messageService.add({
            severity: 'info',
            summary: 'Info',
            detail: res,
          });
        }

        // Add to table if valid
        if (payload.username && payload.functionalities.length > 0) {
          const newRows = payload.functionalities.map((f) => ({
            roleName: payload.roleName,
            userName: payload.username,
            functionality: f,
          }));

          this.userRoleFunctionalityData.push(...newRows);
        }

        this.newRoleName = '';
        this.newFunctionalities = [''];
        this.newUsername = '';
        this.fetchAllRoles();
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Failed',
          detail: err.error || 'An error occurred',
        });
      },
    });
  }

  trackByIndex(index: number): number {
    return index;
  }

  // -------- Table Actions --------

  loadTableData() {
    this.userService.getAllRoleUserFunctionalities().subscribe((data) => {
      this.userRoleFunctionalityData = data;
    });
  }

  onCopy(row: any) {
    console.log('Copy clicked:', row);
  }

  onEdit(row: any) {
    console.log('Edit clicked:', row);
  }

  onDelete(row: any) {
    console.log('Delete clicked:', row);
  }
}