import { Routes } from '@angular/router';
import { Login } from './login/login.component';
import { Register } from './register/register.component';
import { UserHome } from './user-home/user-home.component';
import { AdminDashboard } from './admin-dashboard/admin-dashboard.component';
import { InsuranceSelection } from './insurance-selection/insurance-selection.component';
import { DependantsManagement } from './dependants-management/dependants-management.component';
import { NetworkHospitals } from './network-hospitals/network-hospitals.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'user-dashboard', component: UserHome, canActivate: [AuthGuard] },
  { path: 'admin-dashboard', component: AdminDashboard, canActivate: [AdminGuard] },
  { path: 'insurance-selection', component: InsuranceSelection, canActivate: [AuthGuard] },
  { path: 'dependants-management', component: DependantsManagement, canActivate: [AuthGuard] },
  { path: 'network-hospitals', component: NetworkHospitals, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '/login' }
];
