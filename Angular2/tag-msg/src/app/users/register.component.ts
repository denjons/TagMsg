import { Component } from '@angular/core';


@Component({
  selector: 'tm-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  
   /* 
  constructor(public router: Router, public http: Http) {
  }*/

  register(event, username) {
    /*event.preventDefault();
    let body = JSON.stringify({ username, password });
    this.http.post('http://localhost:3001/users', body, { headers: contentHeaders })
      .subscribe(
        response => {
          localStorage.setItem('id_token', response.json().id_token);
          this.router.navigate(['home']);
        },
        error => {
          alert(error.text());
          console.log(error.text());
        }
      );*/

      event.preventDefault();
      console.log("register user " + username + " uuid: 1234567-112345-1234");
  }


}