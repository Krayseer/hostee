import {Injectable, Optional} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, take} from "rxjs";
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
}
