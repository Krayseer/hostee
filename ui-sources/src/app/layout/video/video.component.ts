import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrl: './video.component.css'
})
export class VideoComponent implements OnInit {
  videoUrl: SafeUrl | undefined;
  token: string | null = null;

  constructor(private http: HttpClient, private roure: ActivatedRoute, private sanitizer: DomSanitizer,
              private router: Router, private snackBar: MatSnackBar) {}

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
    const uuid = this.roure.snapshot.paramMap.get('uuid');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.token
    });
    if (uuid) {
      this.http.get('api/video/' + uuid, {responseType: 'blob', headers: headers}).subscribe(
        (videoBlob: Blob) => {
          const videoUrl = URL.createObjectURL(videoBlob);
          this.videoUrl = this.sanitizer.bypassSecurityTrustUrl(videoUrl);
        }
      )
    }
  }
}
