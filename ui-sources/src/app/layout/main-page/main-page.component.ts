import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Channel} from "../../models/channel";
import {DatePipe} from "@angular/common";

export interface VideoDTO {
  id: number;
  name: string;
  description: string;
  channel: Channel;
  photoUrl: string;
  createdAt: string;
  statistics: VideoStatisticsDTO;
  uuid: string;
}

export interface VideoStatisticsDTO {
  videoId: number;
  countWatches: number;
}

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css',
  providers: [DatePipe]
})
export class MainPageComponent {

  videos: VideoDTO[] = [];
  constructor(private http: HttpClient, private router: Router, private datePipe: DatePipe) {
    this.http.get<VideoDTO[]>("api/video").subscribe(
      videos => {
        this.videos = videos;
        console.log('Список видео: ', this.videos);
      }, error => {
        console.error('Ошибка при получении видео');
      }
    );
  }

  openVideo(id: number) {
    this.router.navigate(['/video', id])
  }

  openChannel(id: number) {
    this.router.navigate(['/channel', id]);
  }

  convertToDate(dateString: string) {
    const date = new Date(dateString);
    return this.datePipe.transform(date, 'd MMMM y', 'ru-RU') || '';
  }
}
