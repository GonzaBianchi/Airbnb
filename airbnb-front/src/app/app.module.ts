import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ServicesComponent } from './components/services/services.component';
import { MyLodgingComponent } from './components/mylodging/mylodging.component';
import { AuthInterceptor } from './interceptors/auth';
import { LodgingsComponent } from './components/lodgings/lodgings.component';
import { LodgingviewComponent } from './components/lodgingview/lodgingview.component';
import { BookingComponent } from './components/booking/booking.component';
import { MybookingsComponent } from './components/mybookings/mybookings.component';
import { LodgingBookingsComponent } from './components/lodging-bookings/lodging-bookings.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    ServicesComponent,
    MyLodgingComponent,
    LodgingsComponent,
    LodgingviewComponent,
    BookingComponent,
    MybookingsComponent,
    LodgingBookingsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
