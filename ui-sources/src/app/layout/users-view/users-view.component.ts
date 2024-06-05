import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../service/UserService";
import {BehaviorSubject, map, Observable, switchMap, take} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

export interface UserSettingDTO {
  pushEnabled: boolean;
  emailEnabled: boolean;
}

export interface UserDTO {
  id: number;
  username: string;
  email: string;
  userSetting: UserSettingDTO;
  blocked: boolean;
}

@Component({
  selector: 'app-users-view',
  templateUrl: './users-view.component.html',
  styleUrl: './users-view.component.css'
})
export class UsersViewComponent implements OnInit{
  users: UserDTO[] = [];
  displayedColumns: string[] = ['username', 'email', 'action'];
  dataSource!: MatTableDataSource<User>;
  resultsLength!: number;
  token: string | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  private subject$: BehaviorSubject<void> = new BehaviorSubject<void>(undefined);

  constructor(private userService: UserService, private router: Router, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.token = localStorage.getItem('token');
    console.log('token', this.token);
    if (this.token == null || this.token == '') {
      this.router.navigate(['sign-in']);
      this.snackBar.open('Пожалуйста, войдите для доступа к этой странице', 'Закрыть', {
        duration: 3000 // Длительность уведомления в миллисекундах (3 секунды)
      });
      return;
    }
    this.loadUsers(this.token);
    this.dataSource = new MatTableDataSource<User>(this.users);
    this.resultsLength = this.users.length;
    this.dataSource.paginator = this.paginator;
  }

  loadUsers(token: string) {
    this.userService.getAllUsers(token).subscribe(users => {
      this.users = users;
      this.dataSource.data = users;
      this.resultsLength = users.length;
      this.dataSource.paginator = this.paginator;
    });
  }

  toggleBlockUser(user: UserDTO): void {
    if (this.token) {
      if (user.blocked) {
        this.unblockUser(user.id);
      } else {
        this.blockUser(user.id);
      }
    }
  }

  unblockUser(userId: number) {
    if (this.token != null) {
      this.userService.unblockUser(userId, this.token).subscribe(() => {
        if (this.token != null) {
          this.loadUsers(this.token);
        }
      });
    }
  }

  blockUser(userId: number) {
    if (this.token != null) {
      this.userService.blockUser(userId, this.token).subscribe(() => {
        if (this.token != null) {
          this.loadUsers(this.token);
        }
      });
    }
  }
}
