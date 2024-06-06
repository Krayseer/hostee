import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  notifications: any[] = [];
  showNotifications: boolean = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.http.get<any[]>('/api/notifications').subscribe(data => {
      this.notifications = data;
    });
  }

  toggleNotifications(): void {
    this.showNotifications = !this.showNotifications;
  }
}
