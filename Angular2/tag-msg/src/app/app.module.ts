import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';

// components
import { AppComponent } from './app.component';
import { RegisterComponent } from './users/register.component'; 

// services
import { UserService } from './users/shared/user.service';

// extensions
import './rxjs-extensions';

@NgModule({
  declarations: [
    AppComponent, RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
