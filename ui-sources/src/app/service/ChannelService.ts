import {Injectable, Optional} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Channel} from "../models/channel";

@Injectable()
export class ChannelService {
  constructor(private http: HttpClient) {
  }

  public getChannel(): Observable<Channel> {
    return this.http.get<Channel>("api/channel");
}
}
