/// <reference types="@angular/localize" />

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';


platformBrowserDynamic().bootstrapModule(AppModule).then(() => {
  // navigator.serviceWorker.register('/firebase-messaging-sw.js');
})
  .catch(err => console.error(err));
