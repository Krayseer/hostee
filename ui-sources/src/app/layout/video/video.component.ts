import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {MatSnackBar} from "@angular/material/snack-bar";
import {PlaylistDTO} from "../playlists/playlists.component";
import {FormControl} from "@angular/forms";

export interface AddVideoInPlaylist{
  playlistId: number,
  videoId: number
}

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrl: './video.component.css'
})
export class VideoComponent implements OnInit {
  videoUrl: SafeUrl | undefined;
  token: string | null = null;
  playlists: PlaylistDTO[] = [];
  selectedPlaylistId: number = -1;
  videoId!: number;

  constructor(private http: HttpClient, private roure: ActivatedRoute, private sanitizer: DomSanitizer,
              private router: Router, private snackBar: MatSnackBar) {}

  onPlaylistSelect(): void {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.token
    });
    const request: AddVideoInPlaylist = {
      videoId: this.videoId,
      playlistId: this.selectedPlaylistId
    }
    this.http.post("api/playlist/video", request, {headers: headers}).subscribe();
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
    const id = this.roure.snapshot.params['uuid'];
    this.videoId = this.roure.snapshot.params['uuid'];

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.token
    });
    if (id) {
      this.http.get('api/video/' + id, {responseType: 'blob', headers: headers}).subscribe(
        (videoBlob: Blob) => {
          const videoUrl = URL.createObjectURL(videoBlob);
          this.videoUrl = this.sanitizer.bypassSecurityTrustUrl(videoUrl);
        }
      );
      this.http.get<PlaylistDTO[]>('api/playlist', {headers: headers}).subscribe(
        playlists => {
          this.playlists = playlists;
          console.log(this.playlists);
        }
      );
    }
  }

  changePlaylistId(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.selectedPlaylistId = Number(selectElement.value);
  }
}
