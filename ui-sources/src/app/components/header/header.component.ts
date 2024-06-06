import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

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
