import { Injectable, NgModule } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  imports: [
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: translateLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ],
  exports: [
    TranslateModule
  ]
})
export class I18nModule {
  constructor(translate: TranslateService, cookie: CookieService) {
    translate.addLangs(['en', 'ta']);
    //const browserLang: any = translate.getBrowserLang();
    let lang = cookie.get("lang");
    translate.use(lang ? lang : 'en');
  }
}

export function translateLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}

@Injectable()
export class SupportedLanguages{

  public static languages : Array<any> = [
    {
      name: 'English',
      code: 'en'
    },
    {
      name: 'Tamil',
      code: 'ta'
    },
  ];

}
