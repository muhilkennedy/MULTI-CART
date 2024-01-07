import { Component, OnInit } from '@angular/core';
import { TasksService } from '../../service/Task/tasks.service'
import { Router } from '@angular/router';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {

  showTaskDialog = false;

  selectedTab: string = 'Not_Started';

  constructor(private router: Router, private taskService: TasksService, private spinner: SpinnerService) {
     
  }

  ngOnInit(): void {
  }

  toggleTaskAction(){
    this.showTaskDialog = !this.showTaskDialog;
  }

  handleCreateTaskModal(event: boolean) {
    this.showTaskDialog = event;
  }

  createTaskAction(){
    this.router.navigate(['/task/createtask']);
  }

}
