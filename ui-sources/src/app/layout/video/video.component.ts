import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrl: './video.component.css'
})
export class VideoComponent implements OnInit {
  videoUrl!: string;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const uuid = 'YOUR_VIDEO_UUID';

    this.http.get('api/video/' + uuid, {responseType: 'blob'}).subscribe(
      (videoBlob: Blob) => {
        const videoUrl = URL.createObjectURL(videoBlob);
        this.videoUrl = videoUrl;
      }
    );
  }
}
