import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';

import { OutboxComponent } from './outbox.component';
import { OutboxListComponent } from './outbox-list/outbox-list.component';
import { OutboxRequestComponent } from './request/outbox.component';



const routes: Routes = [
  {
    path: '',
    component: OutboxComponent,
    children: [
      {
        path: '',
        component: OutboxListComponent,
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
  OutboxListComponent, OutboxComponent, OutboxRequestComponent
];