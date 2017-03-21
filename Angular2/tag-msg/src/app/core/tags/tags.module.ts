import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


// components
import { TagsComponent } from './tags.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  exports:[TagsComponent],
  declarations:[TagsComponent]
})
export class TagsModule { }