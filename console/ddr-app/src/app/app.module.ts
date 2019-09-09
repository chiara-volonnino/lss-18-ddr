import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { MatButtonModule, MatGridListModule, MatTableModule, MatCardModule, MatInputModule, MatMenuModule, MatIconModule, MatToolbarModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { reducers } from './store/reducers';
import { environment } from '../environments/environment'; // Angular CLI environemnt

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WorldComponent } from './world/world.component';
import { RobotComponent } from './robot/robot.component';
import { StateComponent } from './state/state.component';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { WorldMapComponent } from './world-map/world-map.component';
import { HttpClientModule } from '@angular/common/http';
import { WebsocketService } from './websocket.service';
import { RemoterobotService } from './remoterobot.service';
import { TemperatureComponent } from './temperature/temperature.component';
import { TemperatureService } from './temperature.service';

@NgModule({
  declarations: [
    AppComponent,
    WorldComponent,
    RobotComponent,
    StateComponent,
    WorldMapComponent,
    TemperatureComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatGridListModule,
    MatTableModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    StoreModule.forRoot(reducers),
    StoreDevtoolsModule.instrument({
      maxAge: 25, // Retains last 25 states
      logOnly: environment.production, // Restrict extension to log-only mode
    }),
  ],
  providers: [WebsocketService, RemoterobotService, TemperatureService],
  bootstrap: [AppComponent]
})
export class AppModule { }
