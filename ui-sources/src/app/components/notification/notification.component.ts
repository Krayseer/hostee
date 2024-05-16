import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PushNotification} from "../../models/notification";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  notifications: PushNotification[] = [];
  showNotifications: boolean = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.http.get<PushNotification[]>('/api/user/notifications').subscribe(data => {
      this.notifications = data;
    });
  }

  toggleNotifications(): void {
    this.showNotifications = !this.showNotifications;
  }
}
