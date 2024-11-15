import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';
import { PublicGuard } from './guards/public.guard';

import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  { path: 'login',
    component: LoginComponent,
    canActivate: [PublicGuard] 
  },
  { path: 'register',
    component: RegisterComponent,
    canActivate: [PublicGuard] 
  },
  { path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard] 
  },
  { path: '', component: HomeComponent },
  { path: '**', redirectTo: '' }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
