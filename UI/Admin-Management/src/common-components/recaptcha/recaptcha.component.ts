import { Component, OnInit } from '@angular/core';
import { get } from 'scriptjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-recaptcha',
  templateUrl: './recaptcha.component.html',
  styleUrls: ['./recaptcha.component.scss']
})
export class RecaptchaComponent implements OnInit{
  ngOnInit(): void {
    //we need to load this dynamically to make sure captcha container is rendered along with js load.
    get(environment.recaptcha_url, () => {
      console.log("Recaptcha loaded");
    });
  }

}
