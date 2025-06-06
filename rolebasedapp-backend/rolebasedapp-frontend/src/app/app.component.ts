import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // ✅ Step 1: Import CommonModule

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule], // ✅ Step 2: Add CommonModule here
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isLoggedIn = false;

  logout() {
    this.isLoggedIn = false;
    alert('Logged out!');
  }
}
