import { Component, OnInit } from '@angular/core';
import { TodoDto } from 'src/todo-api/src/models';
import { CategoryServiceService } from '../services/category-service.service';
import { TodoServiceService } from '../services/todo-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
@Component({
  selector: 'app-detail-todo',
  templateUrl: './detail-todo.component.html',
  styleUrls: ['./detail-todo.component.scss']
})
export class DetailTodoComponent implements OnInit {
  public errors: string[];

  public todoDto: TodoDto = {
    category: {}
  };
  public categories;

  constructor(
    public categoryService: CategoryServiceService,
    public todoService: TodoServiceService,
    public userService: UserServiceService,
    public router: Router,
    public activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.initCategories();
    this.resolveTodoDto();
  }

  resolveTodoDto() {
    const todoId = this.activatedRoute.snapshot.params.todoId;
    if (todoId) {
      this.todoService.getById(todoId)
        .subscribe(data => {
          this.todoDto = data;
        },
          error => {
            this.cancel();
          });
    }

  }

  initCategories() {
    this.categoryService.getAllByUser(this.userService.getLoggedUser().id)
      .subscribe(data => {
        this.categories = data;
      });
  }


  cancel() {
    this.router.navigate(['todo-list']);
  }

}
