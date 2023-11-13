import { HttpClient } from "@angular/common/http";
import { AfterViewInit, Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie-service";
import { UserService } from "src/app/service/user/user.service";
import { CommonUtil } from "src/app/service/util/common-util.service";
import { environment } from "src/environments/environment";

@Component({
  selector: "app-admin-layout",
  templateUrl: "./admin-layout.component.html",
  styleUrls: ["./admin-layout.component.scss"]
})
export class AdminLayoutComponent implements OnInit, AfterViewInit {
  public sidebarColor: string = "red";

  constructor(private route: Router, private userService: UserService, private cookieService: CookieService) {
    if (CommonUtil.isNullOrEmptyOrUndefined(cookieService.get("X-Token"))) {
      this.route.navigate(['/login']);
    }
    else {
      this.userService.pingUser()
        .subscribe({
          next: (resp: any) => {
            this.userService.getCurrentUser().userEmail = resp.data.emailid;
            this.userService.getCurrentUser().userId = resp.data.rootId;
            this.userService.getCurrentUser().userName = resp.data.fname + resp.data.lname;
          },
          error: (err) => {
            this.cookieService.deleteAll();
            this.route.navigate(['/login']);
          }
        });
    }
  }

  changeSidebarColor(color) {
    var sidebar = document.getElementsByClassName('sidebar')[0];
    var mainPanel = document.getElementsByClassName('main-panel')[0];

    this.sidebarColor = color;

    if (sidebar != undefined) {
      sidebar.setAttribute('data', color);
    }
    if (mainPanel != undefined) {
      mainPanel.setAttribute('data', color);
    }
  }
  changeDashboardColor(color) {
    var body = document.getElementsByTagName('body')[0];
    if (body && color === 'white-content') {
      body.classList.add(color);
    }
    else if (body.classList.contains('white-content')) {
      body.classList.remove('white-content');
    }
  }


  ngOnInit() { }

  ngAfterViewInit() {
    this.registerDragElement();
  }

  private registerDragElement() {
    const elmnt = document.getElementById('mydiv');

    let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;

    const dragMouseDown = (e) => {
      e = e || window.event;
      // get the mouse cursor position at startup:
      pos3 = e.clientX;
      pos4 = e.clientY;
      document.onmouseup = closeDragElement;
      // call a function whenever the cursor moves:
      document.onmousemove = elementDrag;
    };

    const elementDrag = (e) => {
      e = e || window.event;
      // calculate the new cursor position:
      pos1 = pos3 - e.clientX;
      pos2 = pos4 - e.clientY;
      pos3 = e.clientX;
      pos4 = e.clientY;
      // set the element's new position:
      elmnt.style.top = elmnt.offsetTop - pos2 + 'px';
      elmnt.style.left = elmnt.offsetLeft - pos1 + 'px';
    };

    const closeDragElement = () => {
      /* stop moving when mouse button is released:*/
      document.onmouseup = null;
      document.onmousemove = null;
    };

    if (document.getElementById(elmnt.id + 'header')) {
      /* if present, the header is where you move the DIV from:*/
      document.getElementById(elmnt.id + 'header').onmousedown = dragMouseDown;
    } else {
      /* otherwise, move the DIV from anywhere inside the DIV:*/
      elmnt.onmousedown = dragMouseDown;
    }
  }

  public allowDrop(ev): void {
    ev.preventDefault();
  }

  public drag(ev): void {
    ev.dataTransfer.setData("text", ev.target.id);
  }

  public drop(ev): void {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
  }

}
