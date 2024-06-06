import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ChannelService} from "../../service/ChannelService";
import {Channel} from "../../models/channel";
import {UserService} from "../../service/UserService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDTO} from "../main-page/main-page.component";
import {UserDTO} from "../users-view/users-view.component";
import {User} from "../../models/user";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";

export interface VideoRequest {
  name: string;
  description: string;
  video: File;
}

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css'
})
export class ChannelComponent implements OnInit{
  channel!: Channel;
  selectedFile!: File;
  uploadForm: FormGroup = new FormGroup({});
  token: string | null = null;
  videos: VideoDTO[] = [];
  user!: User;
  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(private channelService: ChannelService, private fb: FormBuilder, private userService: UserService,
              private router: Router, private snackBar: MatSnackBar, private http: HttpClient) {}

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
    this.userService.getUser(this.token).subscribe(
      user => {
        this.user = user;
        console.log("Тек пользователь: ", this.user);
      }
    );
    this.getChannel(this.token);
    this.getVideos(this.token);
    this.uploadForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      video: [null, Validators.required]
    });
  }

  getChannel(token: string): void {
    this.channelService.getChannel(token).subscribe(
      channel => {
        this.channel = channel
        console.log("Канал:", this.channel);
        console.log(channel);
      }, error => {
        console.log(error);
        this.router.navigate(['/register-channel']);
      });
  }

  getVideos(token: string) {
    this.channelService.getUserVideos(token).subscribe(
      videos => {
        this.videos = videos;
        console.log("Видео канала: ", this.videos)
      }
    );
  }

  onSubmit(): void {
    if (this.uploadForm.invalid) {
      return;
    }

    const videoRequest: VideoRequest = {
      name: this.uploadForm.get("name")?.value,
      description: this.uploadForm.get('description')?.value,
      video: this.selectedFile
    };

    if (this.token != null) {
      this.channelService.uploadVideo(videoRequest, this.token);
    }
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  onPhotoChange(event: any): void {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.channel.photoUrl = e.target.result; // Предварительный просмотр
        this.uploadPhoto(file); // Загрузка фото на сервер
      };
      reader.readAsDataURL(file);
    }
  }

  uploadPhoto(file: File) {
    const formData = new FormData();
    formData.append('photo', file);

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.token
    });

    this.http.post("api/channel/photo", formData, {headers: headers}).subscribe(
      response => {
        console.log('Фото успешно загружено', response);
        // Обновите URL фото канала после успешной загрузки
      },
      error => {
        console.error('Ошибка загрузки фото', error);
      }
    );
  }

  openVideo(uuid: string) {
    this.router.navigate(['/video', uuid])
  }
}
