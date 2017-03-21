import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'inbox', },
  { path: 'inbox', loadChildren: "app/requests/requests.module#RequestsModule"}
  ,{ path: 'outbox', loadChildren: "app/outbox/outbox.module#OutboxModule"}
  ,{ path: 'msg', loadChildren: "app/msg/msg.module#MSGModule"}
  //{ path: '**', pathMatch: 'full', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

