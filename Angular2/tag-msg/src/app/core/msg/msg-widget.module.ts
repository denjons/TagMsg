import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TagsModule } from '../tags/tags.module';

//import { TagsModule } from '../tags/tags.module';


// components
import { MSGWidgetComponent } from './msg-widget.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule
    ,TagsModule
  ],
  exports:[
      TagsModule, 
  MSGWidgetComponent],
  declarations:[MSGWidgetComponent]
})
export class MSGWidgetModule { }