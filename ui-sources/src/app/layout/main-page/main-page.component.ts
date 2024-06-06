import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Channel} from "../../models/channel";

export interface VideoDTO {
  uuid: string;
  name: string;
  description: string;
  channel: Channel
}


@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent {

  videos: VideoDTO[] = [];
  constructor(private http: HttpClient, private router: Router) {
    this.http.get<VideoDTO[]>("api/video").subscribe(
      videos => {
        this.videos = videos;
        console.log('Список видео: ', this.videos);
      }, error => {
        console.error('Ошибка при получении видео');
      }
    );
  }

  openVideo(uuid: string) {
    this.router.navigate(['/video', uuid])
  }

  openChannel(id: number) {
    this.router.navigate(['/channel', id]);
  }

}
