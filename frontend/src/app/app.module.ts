import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { VirtufileComponent } from './virtufile/virtufile.component';
import { VirtufileService } from './virtufile/virtufile.service';
import { DirectoryComponent } from './directory/directory.component';

@NgModule({
  declarations: [
    AppComponent,
    VirtufileComponent,
    DirectoryComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [VirtufileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
