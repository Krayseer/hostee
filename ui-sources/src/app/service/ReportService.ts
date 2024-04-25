import {Injectable, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BehaviorSubject, Observable, switchMap, take} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {report} from "../models/report";

@Injectable()
export class ReportService {

  constructor(private http: HttpClient) {
  }

  public getReports(): Observable<report[]> {
    return this.http.get<report[]>("/api/report");
  }

  public processReport(id: number){
    return this.http.post('/api/report/process/' + id, null, {
      headers: {'Content-Type': 'application/json'}
    });
  }

  public finishReport(id: number, result: string) {
    return this.http.post('/api/report/finish/' + id, result, {
      headers: {'Content-Type': 'application/json'}
    });
  }

}
