import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RequestsComponent } from './requests.component';
import { RequestListComponent } from './request-list/request-list.component';
import { RequestComponent } from './request/request.component';


const routes: Routes = [
  {
    path: '',
    component: RequestsComponent,
    children: [
      {
        path: '',
        component: RequestListComponent,
      },
      {
        path: ':id',
        component: RequestComponent,
        //canDeactivate: [CanDeactivateGuard],
        //resolve: {
        // vehicle: VehicleResolver
        // }
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RequestsRoutingModule { }

export const routableComponents = [
  RequestsComponent,
  RequestComponent,
  RequestListComponent
];