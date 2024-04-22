import {FormGroup} from "@angular/forms";
import {take} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  /**
   * Зарегистрировать пользователя
   *
   * @param userData данные о пользователе
   */
  public registerUser(userData: string) {
    localStorage.removeItem('token');
    this.http.post<{ jwtToken: string }>(
      'api/user/sign-up',
      userData,
      {
        headers: {'Content-Type': 'application/json'}
      }
    ).subscribe(next => {
        localStorage.setItem('token', next.jwtToken);
      }
    );
  }

  /**
   * Авторизовать пользователя
   *
   * @param authData данные для авторизации пользователя
   */
  public authenticateUser(authData: string) {
    localStorage.removeItem('token');
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
        }
      });
  }
}
