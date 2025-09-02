import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ApexDashboardComponent } from './components/dashboards/apex-dashboard/apex-dashboard.component';
import { Role1DashboardComponent } from './components/dashboards/role1-dashboard/role1-dashboard.component';
import { Role2DashboardComponent } from './components/dashboards/role2-dashboard/role2-dashboard.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'apex', component: ApexDashboardComponent },
  { path: 'role1', component: Role1DashboardComponent },
  { path: 'role2', component: Role2DashboardComponent },
  // Add more routes for other roles as needed  
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
