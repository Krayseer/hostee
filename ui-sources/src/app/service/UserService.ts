import {FormGroup} from "@angular/forms";
import {Observable, take} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {Channel} from "../models/channel";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

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

  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>("api/admin/users");
  }

  blockUser(userId: number): Observable<any> {
    return this.http.post(`api/admin/block/` + userId, null, {
      headers: {'Content-Type': 'application/json'}
    });
  }
}
