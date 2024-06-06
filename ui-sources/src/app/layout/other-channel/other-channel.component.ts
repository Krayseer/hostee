import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../../service/UserService";
import {ChannelService} from "../../service/ChannelService";
import {User} from "../../models/user";
import {Channel} from "../../models/channel";
import {forkJoin} from "rxjs";
import {VideoDTO} from "../main-page/main-page.component";
import {UserSettingDTO} from "../users-view/users-view.component";

export interface UserDTOWithRoles {
  id: number;
  username: string;
  email: string;
  userSetting: UserSettingDTO;
  blocked: boolean;
  roles: string[];
}

@Component({
  selector: 'app-other-channel',
  templateUrl: './other-channel.component.html',
  styleUrl: './other-channel.component.css'
})
export class OtherChannelComponent {

  token: string | null = null;
  user!: UserDTOWithRoles;
  channel!: Channel;
  videos: VideoDTO[] = [];
  subscribedChannels: Channel[] = [];
  channelCurrentUser!: Channel;
  isSubscribed = false;

  constructor(private route: ActivatedRoute, private router: Router, private snackBar: MatSnackBar,
              private userService: UserService, private channelService: ChannelService) {
    this.token = localStorage.getItem('token');
    console.log('token', this.token);

    if (this.token == null || this.token === '') {
      this.router.navigate(['sign-in']);
      this.snackBar.open('Пожалуйста, войдите для доступа к этой странице', 'Закрыть', {
        duration: 3000 // Длительность уведомления в миллисекундах (3 секунды)
      });
      return;
    }

    const userRequest = this.userService.getUserWithRoles(this.token);
    const channelId = this.route.snapshot.params['id'];
    const channelRequest = this.channelService.getChannelFromId(this.token, channelId);
    const currentChannelRequest = this.channelService.getChannel(this.token);

    forkJoin([userRequest, channelRequest, currentChannelRequest]).subscribe(
      ([user, channel, currentChannel]) => {
        this.user = user;
        this.channel = channel;
        this.channelCurrentUser = currentChannel;
        console.log("Текущий пользователь: ", this.user);
        console.log("Текущий канал: ", this.channel);
        console.log("Канал тек пользователя: ", this.channelCurrentUser);

        // Проверка id пользователя и канала
        if (this.channel.user.id === this.user.id) {
          this.router.navigate(['/channel']);
        }
        if (this.token != null) {
          this.channelService.getUserVideosById(this.token, this.user.id).subscribe(
            videos => {
              this.videos = videos;
              console.log("Текущие видео: ", this.videos);
            }
          );
          this.channelService.getSubscribers(this.token, this.channel.id).subscribe(
            channels => {
              this.subscribedChannels = channels;
              console.log('Подписанные каналы:', channels);
              for (const channel of channels) {
                if (channel.id === this.channelCurrentUser.id) {
                  this.isSubscribed = true;
                  console.log('subscribed');
                }
              }
            }
          );
        }
      },
      error => {
        console.error('Ошибка при загрузке данных', error);
        this.snackBar.open('Ошибка при загрузке данных', 'Закрыть', {
          duration: 3000
        });
      }
    );
  }

  openVideo(uuid: string) {
    this.router.navigate(['/video', uuid])
  }

  isAdmin(): boolean {
    return this.user.roles.includes('ADMIN');
  }

  subscribe(channelId: number) {
    if (this.token != null) {
      this.channelService.subscribe(this.token, this.channelCurrentUser.id);
    }
  }

  unsubscribe(channelId: number) {
    console.log('Отписка');
  }

}
