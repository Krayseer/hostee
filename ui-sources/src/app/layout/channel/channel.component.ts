import {Component, OnInit} from '@angular/core';
import {ChannelService} from "../../service/ChannelService";
import {Channel} from "../../models/channel";
import {UserService} from "../../service/UserService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDTO} from "../main-page/main-page.component";

export interface VideoRequest {
  name: string;
  description: string;
  video: File;
}

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css'
})
export class ChannelComponent implements OnInit{
  channel!: Channel;
  selectedFile!: File;
  uploadForm: FormGroup = new FormGroup({});
  token: string | null = null;
  videos: VideoDTO[] = [];

  constructor(private channelService: ChannelService, private fb: FormBuilder,
              private router: Router, private snackBar: MatSnackBar) {}

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
    this.getChannelAndVideos(this.token);
    this.uploadForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      video: [null, Validators.required]
    });
  }

  getChannelAndVideos(token: string): void {
    this.channelService.getChannel(token)
      .subscribe(channel => this.channel = channel);
    this.channelService.getVideos(token).subscribe(
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

    const videoRequest: VideoRequest = {
      name: this.uploadForm.get("name")?.value,
      description: this.uploadForm.get('description')?.value,
      video: this.selectedFile
    };

    if (this.token != null) {
      this.channelService.uploadVideo(videoRequest, this.token);
    }
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  openVideo(uuid: string) {
    this.router.navigate(['/video', uuid])
  }
}
