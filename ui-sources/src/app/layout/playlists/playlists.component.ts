import { Component } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {PlaylistAddModalComponent} from "../../playlist-add-modal/playlist-add-modal.component";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDTO} from "../main-page/main-page.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";

export interface PlaylistDTO {
  id: number,
  name: string,
  description: string,
  videos: VideoDTO[]
}

@Component({
  selector: 'app-playlists',
  templateUrl: './playlists.component.html',
  styleUrl: './playlists.component.css'
})
export class PlaylistsComponent {

  token: string | null = null;
  playlists: PlaylistDTO[] = [];

  constructor(public dialog: MatDialog, private router: Router, private snackBar: MatSnackBar, private http: HttpClient) {
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
    this.http.get<PlaylistDTO[]>("api/playlist", {headers: headers}).subscribe(
      playlists => {
        this.playlists = playlists;
        console.log('Плейлисты', this.playlists);
      }
    );
  }

  openPlaylistDialog() {
    const dialogRef = this.dialog.open(PlaylistAddModalComponent, {

    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Модальное окно закрыто');
      // Логика после закрытия модального окна, если необходимо
    });
  }

  openPlaylist(playlistId: number): void {
    // Перенаправление на другую страницу с передачей идентификатора плейлиста в качестве параметра
    this.router.navigate(['/playlist-videos', playlistId]);
  }

}
