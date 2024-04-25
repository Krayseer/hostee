import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BehaviorSubject, catchError, map, Observable, switchMap, take, throwError} from "rxjs";
import {ReportService} from "../../service/ReportService";
import {report} from "../../models/report";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit {

  userForm!: FormGroup

  public readonly reports$: Observable<report[]>;

  private subject$: BehaviorSubject<void> = new BehaviorSubject<void>(undefined);

  constructor(private fb: FormBuilder,
              private reportService: ReportService) {
    this.reports$ = this.subject$.pipe(
      switchMap(() => this.reportService.getReports()),
      map(reports => reports.sort((a, b) => a.id - b.id))
    );
  }

  ngOnInit() {
    this.userForm = this.fb.group({
      id: ['', Validators.required],
      userTarget: ['', Validators.required],
      userSender: ['', Validators.required],
      text: ['', Validators.required]
    });
  }

  process(id: number, reportState: string) {
    if (reportState === 'WAIT_PROCESS') {
      this.reportService.processReport(id).pipe(take(1)).subscribe(() => this.subject$.next());
    } else if (reportState === 'IN_PROCESS') {
      this.reportService.finishReport(id, "Сделали предупреждение пользователю.").pipe(take(1))
        .subscribe(() => this.subject$.next());
    }
  }

  getButtonText(reportState: string) {
    switch(reportState) {
      case 'WAIT_PROCESS':
        return 'Взять в обработку';
      case 'IN_PROCESS':
        return 'Отправить ответ';
      default:
        return 'Нет кнопки!';
    }
  }

  getReportColor(reportState: string) {
    switch(reportState) {
      case 'WAIT_PROCESS':
        return 'red';
      case 'IN_PROCESS':
        return 'yellow';
      case 'PROCESSED':
        return 'green';
      default:
        return 'black';
    }
  }

}
