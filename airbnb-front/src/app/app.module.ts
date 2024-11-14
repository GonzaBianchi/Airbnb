import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { HomeComponent } from './component/home/home.component';
import { ReservaComponent } from './component/reserva/reserva.component';
import { HospedajeComponent } from './component/hospedaje/hospedaje.component';
import { ServicioComponent } from './component/servicio/servicio.component';

@NgModule({
  declarations: [
    AppComponent,
    ReservaComponent,
    HospedajeComponent,
    ServicioComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    LoginComponent,
    RegisterComponent,
    HomeComponent
  ],
  
  providers: [
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
