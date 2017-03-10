import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RequestListComponent } from './requests/request-list/request-list.component';
import { RequestComponent } from './requests/request/request.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'requests', },
  { path: 'requests', loadChildren: "app/requests/requests.module#RequestsModule"},
  //{ path: '**', pathMatch: 'full', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routableComponents = [
  RequestComponent,
  RequestListComponent
];