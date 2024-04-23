import {Component, OnInit} from '@angular/core';
import {ChannelService} from "../../service/ChannelService";
import {Channel} from "../../models/channel";
import {UserService} from "../../service/UserService";

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css'
})
export class ChannelComponent implements OnInit{
  channel!: Channel;

  constructor(private channelService: ChannelService) {}

  ngOnInit(): void {
    this.getChannel();
  }

  getChannel(): void {
    this.channelService.getChannel()
      .subscribe(channel => this.channel = channel);
  }
}
