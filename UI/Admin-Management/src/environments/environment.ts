// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backendProxy: "http://localhost:8080/tm",
  productProxy: "http://localhost:8080/pm",
  tenantId: "devTenant",
  tenant: "Dev Tenant",
  google_oauth_client: "18310117289-aq54dq89ad3kolmuam4qogj88vuep7lv.apps.googleusercontent.com",
  recaptcha_url: "https://www.google.com/recaptcha/api.js",
  captcha_sitekey: "6LcyW5wjAAAAAB0YWbAhaaj4F8S0XdmifvDo_2u9",
  firebaseConfig : {
    apiKey: "AIzaSyB5KN1SPyfP53zSfc35SST7LqKAH-EFn80",
    authDomain: "mken-test-webapp.firebaseapp.com",
    databaseURL: "https://mken-test-webapp-default-rtdb.firebaseio.com",
    projectId: "mken-test-webapp",
    storageBucket: "mken-test-webapp.appspot.com",
    messagingSenderId: "457825634005",
    appId: "1:457825634005:web:d945f7e10f7f4c01d1445c",
    measurementId: "G-2WP00DGCLG"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
