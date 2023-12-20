importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js')
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js')

// The object we pass as an argument is the same object we copied into the environment files
const app = initializeApp({
  apiKey: "AIzaSyB5KN1SPyfP53zSfc35SST7LqKAH-EFn80",
  authDomain: "mken-test-webapp.firebaseapp.com",
  databaseURL: "https://mken-test-webapp-default-rtdb.firebaseio.com",
  projectId: "mken-test-webapp",
  storageBucket: "mken-test-webapp.appspot.com",
  messagingSenderId: "457825634005",
  appId: "1:457825634005:web:d945f7e10f7f4c01d1445c",
  measurementId: "G-2WP00DGCLG"
})

const messaging = getMessaging(app);

// import { initializeApp } from "https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js";
// import { getMessaging } from "https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js";
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