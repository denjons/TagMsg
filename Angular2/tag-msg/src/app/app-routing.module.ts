import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'requests', },
  { path: 'requests', loadChildren: "app/requests/requests.module#RequestsModule"}
  ,{ path: 'outbox', loadChildren: "app/outbox/outbox.module#OutboxModule"}
  //{ path: '**', pathMatch: 'full', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

