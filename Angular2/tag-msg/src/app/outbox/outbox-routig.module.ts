import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';

import { OutboxRoutingComponent } from './outbox-routing.component';
import { OutboxComponent } from './outbox.component';
import { OutboxRequestComponent } from './request/outbox.component';



const routes: Routes = [
  {
    path: '',
    component: OutboxRoutingComponent,
    children: [
      {
        path: '',
        component: OutboxComponent,
      }
      ,{
        path: ':id',
        component: OutboxRequestComponent,
        //canDeactivate: [CanDeactivateGuard],
        //resolve: {
        // vehicle: VehicleResolver
        // }
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OutboxRoutingModule { }

export const routableComponents = [
  OutboxComponent, OutboxRoutingComponent, OutboxRequestComponent
];