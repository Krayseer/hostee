import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VideoDTO} from "../main-page/main-page.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DatePipe} from "@angular/common";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {PlaylistDTO} from "../playlists/playlists.component";


@Component({
  selector: 'app-videos',
  templateUrl: './videos.component.html',
  styleUrl: './videos.component.css',
  providers: [DatePipe]
})
export class VideosComponent implements OnInit {

  videos: VideoDTO[] = [];
  token: string | null = null;

  constructor(private roure: ActivatedRoute, private router: Router, private snackBar: MatSnackBar, private datePipe: DatePipe,
              private http: HttpClient) {
  }

  ngOnInit(): void {
    const id = this.roure.snapshot.params['id'];
    console.log(id);

    this.token = localStorage.getItem('token');
    console.log('token', this.token);
    if (this.token == null || this.token == '') {
      this.router.navigate(['sign-in']);
      this.snackBar.open('Пожалуйста, войдите для доступа к этой странице', 'Закрыть', {
        duration: 3000 // Длительность уведомления в миллисекундах (3 секунды)
      });
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.token
    });
    this.http.get<PlaylistDTO>("api/playlist/" + id, {headers: headers}).subscribe(
      data => {
        this.videos = data.videos;
        console.log(this.videos);
      }
    );
  }

  convertToDate(dateString: string) {
    const date = new Date(dateString);
    return this.datePipe.transform(date, 'd MMMM y', 'ru-RU') || '';
  }

  openVideo(id: number) {
    this.router.navigate(['/video', id])
  }

}
