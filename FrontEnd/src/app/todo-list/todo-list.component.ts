import { Component, OnInit } from '@angular/core';

import { CategoryServiceService } from '../services/category-service.service';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {

  public categories;
  intialiseObj: any = [
    {
      "id": 2,
      "name": "Problem Solving",
      "description": "this is Problem Solving 3",
      "user": null,
      "todoList": [
        {
          "id": 5,
          "title": "lop",
          "description": "lop",
          "startDate": "2023-09-20T13:18:29+02:00",
          "done": false,
          "favorite": false,
          "category": null
        },
        {
          "id": 14,
          "title": "llll",
          "description": "llll",
          "startDate": "2023-09-20T13:18:21+02:00",
          "done": false,
          "favorite": false,
          "category": null
        }
      ]
    },
    {
      "id": 7,
      "name": "Novoroute",
      "description": "sdc",
      "user": null,
      "todoList": [
        {
          "id": 12,
          "title": "da",
          "description": null,
          "startDate": "2023-09-20T11:15:02+02:00",
          "done": false,
          "favorite": false,
          "category": null
        },
        {
          "id": 9,
          "title": "popa",
          "description": null,
          "startDate": "2023-09-20T11:14:58+02:00",
          "done": false,
          "favorite": false,
          "category": null
        }
      ]
    }
  ];
  fake: boolean = true;


  constructor(
    public categoryService: CategoryServiceService,
    public activatedRouter: ActivatedRoute,
    public userService: UserServiceService
  ) { }

  ngOnInit(): void {

    this.resolveTimeParamerters();
  }

  resolveTimeParamerters() {
    const dateTime = this.activatedRouter.snapshot.params.start;
    if (dateTime && dateTime === 'today') {
      this.categoryService.getAllForToday(this.userService.getLoggedUser().id)
        .subscribe(data => {
          this.categories = data;
        });
    } else {
      this.initCategories();
    }
  }

  initCategories() {
    this.categoryService.getAllByUser(this.userService.getLoggedUser().id)
      .subscribe(data => {
        if (data.length == 0) {
          this.categories = this.intialiseObj;
          this.fake = true
        }
        else {
          this.categories = data;
          this.fake = false
        }

      });
  }

}
