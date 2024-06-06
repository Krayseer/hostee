import {Injectable, Optional} from "@angular/core";
import {Observable, take} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Channel} from "../models/channel";
import {VideoRequest} from "../layout/channel/channel.component";
import {VideoDTO} from "../layout/main-page/main-page.component";

@Injectable()
export class ChannelService {
  constructor(private http: HttpClient) {
  }

  public getChannel(token: string): Observable<Channel> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<Channel>("api/channel", {headers: headers});
}

  public getChannelFromId(token: string, id: number): Observable<Channel> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<Channel>("api/channel/" + id, {headers: headers});
  }

  public getVideos(token: string): Observable<VideoDTO[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<VideoDTO[]>("api/video", {headers: headers});
  }

  public getUserVideos(token: string): Observable<VideoDTO[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<VideoDTO[]>("api/video/user", {headers: headers});
  }

  public getUserVideosById(token: string, userId: number): Observable<VideoDTO[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    return this.http.get<VideoDTO[]>("api/video/user/" + userId, {headers: headers});
  }

  public registerChannel(userData: string, token: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    });
    this.http.post("api/channel", userData, {
      headers: headers
    }).pipe(take(1)).subscribe();
  }

  uploadPhoto(formData: FormData, token: string) {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    this.http.post("api/channel/photo", formData, {headers: headers}).subscribe();
  }

  subscribe(token: string, channelId: number) {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    this.http.post("api/subscribe", channelId, {headers: headers}).subscribe();
  }

  getSubscribers(token: string, channelId: number): Observable<Channel[]> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    return this.http.get<Channel[]>("api/subscribe/" + channelId, {headers: headers});
  }

  uploadVideo(videoRequest: VideoRequest, token: string): void {
    const formData = new FormData();
    formData.append('name', videoRequest.name);
    formData.append('description', videoRequest.description);
    formData.append('video', videoRequest.video);

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    this.http.post("api/video", formData, { headers: headers }).subscribe(
      () => {
        // Обработка успешной загрузки видео
      },
      error => {
        // Обработка ошибки загрузки видео
      }
    );
  }
}
