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
import {DatePipe} from "@angular/common";

export interface VideoRequest {
  name: string;
  description: string;
  video: File;

}

export interface PlaylistDTO {
  id: number;
  name: string;
  description: string;
}

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css',
  providers: [DatePipe]
})
export class ChannelComponent implements OnInit{
  channel!: Channel;
  thumbnailFile: File | null = null;
  selectedFile!: File;
  uploadForm: FormGroup = new FormGroup({});
  token: string | null = null;
  videos: VideoDTO[] = [];
  sortOptions = ['Дата (новые)', 'Дата (старые)', 'Просмотры (возрастание)', 'Просмотры (убывание)'];
  selectedSortOption: string = this.sortOptions[0];
  user!: User;
  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(private channelService: ChannelService, private fb: FormBuilder, private userService: UserService,
              private router: Router, private snackBar: MatSnackBar, private http: HttpClient, private datePipe: DatePipe) {}

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
      video: [null, Validators.required],
      thumbnail: [null, Validators.required]
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

    const formData = new FormData();
    formData.append('name', this.uploadForm.get('name')?.value);
    formData.append('description', this.uploadForm.get('description')?.value);

    if (this.selectedFile) {
      formData.append('video', this.selectedFile);
    }
    if (this.thumbnailFile) {
      formData.append('preview', this.thumbnailFile);
    }

    if (this.token != null) {
      this.channelService.uploadVideo(formData, this.token);
    }
  }

  onFileChange(event: Event, fileType: string): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      if (fileType === 'video') {
        this.selectedFile = input.files[0];
      } else if (fileType === 'thumbnail') {
        this.thumbnailFile = input.files[0];
      }
    }
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

  sortVideos() {
    switch (this.selectedSortOption) {
      case 'Дата (новые)':
        this.videos.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
        break;
      case 'Дата (старые)':
        this.videos.sort((a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
        break;
      case 'Просмотры (возрастание)':
        this.videos.sort((a, b) => a.statistics.countWatches - b.statistics.countWatches);
        break;
      case 'Просмотры (убывание)':
        this.videos.sort((a, b) => b.statistics.countWatches - a.statistics.countWatches);
        break;
      default:
        break;
    }
  }

  onSortChange(): void {
    this.sortVideos();
  }

  convertToDate(dateString: string) {
    const date = new Date(dateString);
    return this.datePipe.transform(date, 'd MMMM y', 'ru-RU') || '';
  }

  openVideo(id: number) {
    this.router.navigate(['/video', id])
  }

  download(id: number) {
    this.http.get("api/statistics/channel/" + id + "/pdf").subscribe();
  }
}
