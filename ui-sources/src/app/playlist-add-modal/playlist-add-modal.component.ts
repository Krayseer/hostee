import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {HttpClient} from "@angular/common/http";

export interface PlaylistRequest {
  name: string,
  description: string
}

@Component({
  selector: 'app-playlist-add-modal',
  templateUrl: './playlist-add-modal.component.html',
  styleUrl: './playlist-add-modal.component.css'
})
export class PlaylistAddModalComponent {

  playlistName: string = '';
  playlistDescription: string = '';

  constructor(public dialogRef: MatDialogRef<PlaylistAddModalComponent>, public http: HttpClient) {}

  close(): void {
    this.dialogRef.close();
  }

  submit(): void {
    const request: PlaylistRequest = {
      name: this.playlistName,
      description: this.playlistDescription
    };

    this.http.post('api/playlist', request)
      .subscribe(response => {
        console.log('Playlist created successfully', response);
        this.close(); // Закрыть модальное окно после успешного запроса
      }, error => {
        console.error('Error creating playlist', error);
      });
  }

}
