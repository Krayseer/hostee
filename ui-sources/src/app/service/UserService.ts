import {FormGroup} from "@angular/forms";
import {Observable, take} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {Channel} from "../models/channel";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {UserDTO} from "../layout/users-view/users-view.component";
import {UserDTOWithRoles} from "../layout/other-channel/other-channel.component";

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private snackBar: MatSnackBar, private router: Router) {
  }

  /**
   * Зарегистрировать пользователя
   *
   * @param userData данные о пользователе
   */
  public registerUser(userData: string) {
    localStorage.clear();
    this.http.post<{ jwtToken: string }>(
      'api/user/sign-up',
      userData,
      {
        headers: {'Content-Type': 'application/json'}
      }
    ).subscribe(
      next => {
        localStorage.setItem('token', next.jwtToken);
        this.router.navigate(['/sign-in']);
      },
      error => {
        // Обработка ошибки
        if (error.error && error.error.message.includes("User with username")) {
          console.error('Ошибка регистрации:', error);
          this.snackBar.open('Такой пользователь уже существует', 'Закрыть', {
            duration: 5000
          });
        }
      }
    );
  }

  /**
   * Авторизовать пользователя
   *
   * @param authData данные для авторизации пользователя
   */
  public authenticateUser(authData: string) {
    localStorage.clear();
    this.http.post<{ jwtToken: string }>(
      'api/user/sign-in',
      authData,
      {
        headers: {'Content-Type': 'application/json'}
      }
    ).pipe(
        take(1)
    ).subscribe({
      next: next => {
        localStorage.setItem('token', next.jwtToken);
        this.router.navigate(['/main']);
      },
      error: error => {
        // Обработка ошибки
        if (error.error && error.error.message.includes("Authenticate error user")) {
          console.error('Ошибка авторизации: ', error);
          this.snackBar.open('Неверные данные', 'Закрыть', {
            duration: 5000
          });
        }
      }
    });
  }

  public getUser(token: string):Observable<User> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<User>("api/user", {headers: headers});
  }

  public getUserWithRoles(token: string):Observable<UserDTOWithRoles> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<UserDTOWithRoles>("api/user", {headers: headers});
  }

  public getAllUsers(token: string): Observable<UserDTO[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<UserDTO[]>("api/user/all", {headers: headers});
  }

  blockUser(userId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.post(`api/user/block/` + userId, null, {
      headers: {'Content-Type': 'application/json'}
    });
  }

  unblockUser(userId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.post(`api/user/unblock/` + userId, null, {
      headers: {'Content-Type': 'application/json'}
    });
  }
}
