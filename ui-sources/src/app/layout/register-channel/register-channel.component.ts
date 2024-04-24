import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/UserService";
import {ChannelService} from "../../service/ChannelService";

@Component({
  selector: 'app-register-channel',
  templateUrl: './register-channel.component.html',
  styleUrl: './register-channel.component.css'
})
export class RegisterChannelComponent implements OnInit {

  registerChannelForm!: FormGroup;

  constructor(private fb: FormBuilder,
              private channelService: ChannelService) {
  }

  ngOnInit() {
    this.registerChannelForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit() {
    const channelRegisterData = JSON.stringify(this.registerChannelForm.value);
    this.channelService.registerChannel(channelRegisterData);
  }
}
