import {Injectable, Optional} from "@angular/core";
import {Observable, take} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Channel} from "../models/channel";

@Injectable()
export class ChannelService {
  constructor(private http: HttpClient) {
  }

  public getChannel(): Observable<Channel> {
    return this.http.get<Channel>("api/channel");
}

  public registerChannel(userData: string) {
    this.http.post("api/channel", userData, {
      headers: {'Content-Type': 'application/json'}
    }).pipe(take(1)).subscribe();
  }

  uploadVideo(formData: FormData): void {
    this.http.post("api/video", formData);
  }
}
