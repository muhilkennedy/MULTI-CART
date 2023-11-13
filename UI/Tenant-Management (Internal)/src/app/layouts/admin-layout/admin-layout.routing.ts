import { Routes } from "@angular/router";
import { EdittenantComponent } from "src/app/pages/edittenant/edittenant.component";
import { ManageuserComponent } from "src/app/pages/manageuser/manageuser.component";
import { OnboardComponent } from "src/app/pages/onboard/onboard.component";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
// import { RtlComponent } from "../../pages/rtl/rtl.component";

export const AdminLayoutRoutes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "onboard", component: OnboardComponent },
  { path: "edittenant", component: EdittenantComponent },
  { path: "manageuser", component: ManageuserComponent }
];
