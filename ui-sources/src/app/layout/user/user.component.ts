import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/UserService";
import {User} from "../../models/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit{
  user!: User;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    let token = localStorage.getItem('token');
    console.log('token', token);
    if (token == null || token == '') {
      this.router.navigate(['sign-in']);
      return;
    }
    this.userService.getUser(token)
      .subscribe(user => this.user = user);
  }
}
