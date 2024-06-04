import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/UserService";
import {ChannelService} from "../../service/ChannelService";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register-channel',
  templateUrl: './register-channel.component.html',
  styleUrl: './register-channel.component.css'
})
export class RegisterChannelComponent implements OnInit {

  registerChannelForm!: FormGroup;
  token: string | null = null;

  constructor(private fb: FormBuilder, private userService: UserService, private http: HttpClient,
              private channelService: ChannelService, private router: Router, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    console.log('token', this.token);
    if (this.token == null || this.token == '') {
      this.router.navigate(['sign-in']);
      this.snackBar.open('Пожалуйста, войдите для доступа к этой странице', 'Закрыть', {
        duration: 3000 // Длительность уведомления в миллисекундах (3 секунды)
      });
      return;
    }
    this.userService.getUser(this.token).subscribe(
      user => {
        console.log('Текущий пользователь: ', user);
      }, error => {
        console.log(error);
        this.router.navigate(['/sign-in']);
        this.snackBar.open('Пожалуйста, войдите для доступа к этой странице', 'Закрыть', {
          duration: 3000 // Длительность уведомления в миллисекундах (3 секунды)
        });
      }
    );
    this.registerChannelForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.token == null || this.token == '') {
      this.router.navigate(['sign-in']);
      return;
    }
    const channelRegisterData = JSON.stringify(this.registerChannelForm.value);
    this.channelService.registerChannel(channelRegisterData, this.token);
  }
}
