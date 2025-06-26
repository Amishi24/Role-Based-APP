import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../interfaces/user';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  users: User[] = [];
  selectedUser: User = { firstName: '', lastName: '', email: '', role: '', approved: false };
  showAddEditForm = false;
  isEditMode = false;
  showUserDetails = false;
  activeSection: string = 'dashboard';

  constructor(
    private userService: UserService,
    private router: Router,
    private msgService: MessageService
  ) {}

  ngOnInit(): void {
    this.getUsers();
  }

  setSection(section: string) {
    this.activeSection = section;

    if (section === 'addUser') {
      this.selectedUser = { firstName: '', lastName: '', email: '', role: '', approved: false };
      this.isEditMode = false;
      this.showAddEditForm = false;
    }
    if (section === 'userMgmt') {
      this.getUsers();  // Load fresh data when visiting userMgmt
    }
  }

  getUsers() {
    this.userService.getUsersList().subscribe({
      next: data => {
        console.log("Fetched users:", data);
        this.users = data;
      },
      error: err => {
        console.error("Error fetching users:", err);
      }
    });
  }

  addUserDialog() {
    this.selectedUser = { firstName: '', lastName: '', email: '', role: '', approved: false };
    this.isEditMode = false;
    this.showAddEditForm = true;
  }

  updateUser(id: number) {
    this.userService.getUserById(id).subscribe(data => {
      const matchedUser = this.users.find(u => u.id === id);
      const approved = matchedUser?.approved ?? false;
      this.selectedUser = { ...data, id, approved };
      this.isEditMode = true;
      this.showAddEditForm = true;
    });
  }

  saveUser() {
    if (this.isEditMode && this.selectedUser.id) {
      const updatePayload = {
        firstName: this.selectedUser.firstName,
        lastName: this.selectedUser.lastName,
        email: this.selectedUser.email,
        role: this.selectedUser.role,
        approved: this.selectedUser.approved ?? false
      };
      this.userService.updateUserById(this.selectedUser.id, updatePayload).subscribe(() => {
        this.userService.getUserById(this.selectedUser.id!).subscribe(updated => {
          this.selectedUser = { ...updated, id: this.selectedUser.id };
        });
        this.getUsers();
        this.showAddEditForm = false;
        this.activeSection = 'userMgmt';
        this.msgService.add({ severity: 'success', summary: 'Success', detail: 'User updated successfully' }); 
      });
    } else {
      this.userService.createUser(this.selectedUser).subscribe(() => {
        this.getUsers();
        this.showAddEditForm = false;
        this.activeSection = 'userMgmt';
        this.msgService.add({ severity: 'success', summary: 'Success', detail: 'User added successfully' }); 
      });
    }
  }

  deleteUser(id?: number) {
    if (id && confirm('Are you sure to delete this user?')) {
      this.userService.deleteUser(id).subscribe(() => {
        this.getUsers();
        this.msgService.add({ severity: 'success', summary: 'Deleted', detail: 'User deleted successfully' }); 
      });
    }
  }

  approveUser(id: number) {
    this.userService.approveUser(id).subscribe(() => {
      this.getUsers();
    });
  }

  rejectUser(id: number) {
    this.userService.rejectUser(id).subscribe(() => {
      this.getUsers();
    });
  }

  userDetails(id: number) {
    const user = this.users.find(u => u.id === id);
    if (user) {
      this.selectedUser = user;
      this.showUserDetails = true;
    }
  }

  cancel() {
    this.showAddEditForm = false;
    this.showUserDetails = false;
    this.isEditMode = false;
    this.activeSection = 'userMgmt';
  }

  logOut() {
    localStorage.clear();
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}