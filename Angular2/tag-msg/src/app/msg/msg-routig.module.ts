import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';

import { MSGComponent } from './msg.component';
import { MSGRoutingComponent } from './msg-routing.component';



const routes: Routes = [
  {
    path: '',
    component: MSGRoutingComponent,
    children: [
      {
        path: '',
        component: MSGComponent,
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MSGRoutingModule { }

export const routableComponents = [
  MSGRoutingComponent, MSGComponent
];