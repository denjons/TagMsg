import { Headers } from '@angular/http';



export class TMHeader{

     static headers = new Headers();
     static ATYHORIZATION = "authorization";
}

TMHeader.headers.append('Accept', 'application/json');
TMHeader.headers.append('Content-Type', 'application/json');
/*
TMHeader.headers.append('Access-Control-Allow-Origin', 'http://localhost:4200');
TMHeader.headers.append("Access-Control-Allow-Origin", "*");
TMHeader.headers.append("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
TMHeader.headers.append("Access-Control-Allow-Credentials", "true");
TMHeader.headers.append("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
TMHeader.headers.append("Access-Control-Max-Age", "1209600");
*/




