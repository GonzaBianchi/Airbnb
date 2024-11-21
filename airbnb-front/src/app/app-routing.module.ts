import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';
import { PublicGuard } from './guards/public.guard';
import { AdminGuard } from './guards/admin.guard';

import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ServicesComponent } from './components/services/services.component';
import { MyLodgingComponent } from './components/mylodging/mylodging.component';
import { LodgingsComponent } from './components/lodgings/lodgings.component';
import { LodgingviewComponent } from './components/lodgingview/lodgingview.component';
import { BookingComponent } from './components/booking/booking.component';
import { MybookingsComponent } from './components/mybookings/mybookings.component';
import { LodgingBookingsComponent } from './components/lodging-bookings/lodging-bookings.component';

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
  { path: 'services',
    component: ServicesComponent,
    canActivate: [AdminGuard] 
  },
  { path: 'mis-hospedajes',
    component: MyLodgingComponent,
    canActivate: [AuthGuard]
  },
  { path: 'buscar-hospedajes',
    component: LodgingsComponent,
    canActivate: [AuthGuard]
  },
  { path: 'hospedaje/:id',
    component: LodgingviewComponent,
    canActivate: [AuthGuard]
  },
  { path: 'reservas/:id', 
    component: BookingComponent,
    canActivate: [AuthGuard]
  },
  { path: 'mis-reservas',
    component: MybookingsComponent,
    canActivate: [AuthGuard]
  },
  { path: 'ver-reservas',
    component: LodgingBookingsComponent,
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
