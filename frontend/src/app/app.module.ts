import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenubarModule} from 'primeng/menubar'
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { InputSwitchModule } from 'primeng/inputswitch';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { HttpClientModule } from '@angular/common/http';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';
import { TabViewModule } from 'primeng/tabview';


import { ApexDashboardComponent } from './components/dashboards/apex-dashboard/apex-dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { Role1DashboardComponent } from './components/dashboards/role1-dashboard/role1-dashboard.component';
import { Role2DashboardComponent } from './components/dashboards/role2-dashboard/role2-dashboard.component';
import { SidebarMenuComponent } from './components/shared/sidebar-menu/sidebar-menu.component';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ApexDashboardComponent,
    Role1DashboardComponent,
    Role2DashboardComponent,
    SidebarMenuComponent,
    // Add other components here
  ],
  imports: [
    BrowserModule,
    TabViewModule,
    PanelModule,
    AppRoutingModule,
    CardModule,
    InputTextModule,
    MenubarModule,
    ReactiveFormsModule,
    ButtonModule,
    HttpClientModule,
    ToastModule,
    BrowserAnimationsModule,
    FormsModule,
    TableModule,
    DialogModule,
    CheckboxModule,
    DropdownModule,
    InputSwitchModule
  ],
  providers: [MessageService,
  
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
