import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../service/UserService";
import {BehaviorSubject, map, Observable, switchMap, take} from "rxjs";

@Component({
  selector: 'app-users-view',
  templateUrl: './users-view.component.html',
  styleUrl: './users-view.component.css'
})
export class UsersViewComponent implements OnInit{
  users: User[] = [];

  private subject$: BehaviorSubject<void> = new BehaviorSubject<void>(undefined);

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }

  blockUser(userId: number) {
    this.userService.blockUser(userId).subscribe(() => {
      this.loadUsers();
    });
  }
}
