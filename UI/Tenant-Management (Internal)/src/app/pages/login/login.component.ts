import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from 'src/app/service/user/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public email: string;
  public password: string;

  constructor(private http: HttpClient,
    private route: Router,
    private userService: UserService,
    private cookieService: CookieService,
    public translate: TranslateService) { }

  ngOnInit(): void {
  }

  login() {
    const body = {
      emailId: this.email,
      password: this.password
    };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    this.userService.login(body)
      .subscribe(
        {
          next: (resp: HttpResponse<any>) => {
            this.userService.getCurrentUser().userEmail = resp.body.data.emailid;
            this.userService.getCurrentUser().userId = resp.body.data.rootId;
            this.userService.getCurrentUser().userName = resp.body.data.fname + resp.body.data.lname;
            this.userService.getCurrentUser().token = resp.headers.get("X-Token");;
            this.cookieService.set("X-Token", this.userService.getCurrentUser().token);
            this.route.navigate(['/dashboard']);
          },
          error: (err) => {
            console.log("error in loading tenant");
            alert("Invalid Creds!");
          }
        }
      );
  }

}
