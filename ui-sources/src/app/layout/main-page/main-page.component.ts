import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

export interface VideoDTO {
  uuid: string;
  name: string;
  description: string;
}


@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent {

  videos: VideoDTO[] = [];
  constructor(private http: HttpClient) {
    this.http.get<VideoDTO[]>("api/video/").subscribe(
      videos => {
        this.videos = videos;
        console.log('Список видео: ', this.videos);
      }, error => {
        console.error('Ошибка при получении видео');
      }
    );
  }

}
