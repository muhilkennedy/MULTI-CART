/// <reference types="@angular/localize" />

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';


platformBrowserDynamic().bootstrapModule(AppModule).then(() => {
  //navigator.serviceWorker.register('/firebase-messaging-sw.js');
})
  .catch(err => console.error(err));

  // Import the functions you need from the SDKs you need
// import { initializeApp } from "firebase/app";
// import { getMessaging } from "firebase/messaging";
// // TODO: Add SDKs for Firebase products that you want to use
// // https://firebase.google.com/docs/web/setup#available-libraries

// // Your web app's Firebase configuration
// // For Firebase JS SDK v7.20.0 and later, measurementId is optional
// const firebaseConfig = {
//   apiKey: "AIzaSyB5KN1SPyfP53zSfc35SST7LqKAH-EFn80",
//   authDomain: "mken-test-webapp.firebaseapp.com",
//   databaseURL: "https://mken-test-webapp-default-rtdb.firebaseio.com",
//   projectId: "mken-test-webapp",
//   storageBucket: "mken-test-webapp.appspot.com",
//   messagingSenderId: "457825634005",
//   appId: "1:457825634005:web:d945f7e10f7f4c01d1445c",
//   measurementId: "G-2WP00DGCLG"
// };

// // Initialize Firebase
// const app = initializeApp(firebaseConfig);
// const analytics = getMessaging(app);
