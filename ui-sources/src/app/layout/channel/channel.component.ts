import {Component, OnInit} from '@angular/core';
import {ChannelService} from "../../service/ChannelService";
import {Channel} from "../../models/channel";
import {UserService} from "../../service/UserService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css'
})
export class ChannelComponent implements OnInit{
  channel!: Channel;
  selectedFile: any;
  uploadForm: FormGroup = new FormGroup({});

  constructor(private channelService: ChannelService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.getChannel();
    this.uploadForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      file: [null, Validators.required]
    });
  }

  getChannel(): void {
    this.channelService.getChannel()
      .subscribe(channel => this.channel = channel);
  }

  onSubmit(): void {
    if (this.uploadForm.invalid) {
      return;
    }

    const formData : FormData = new FormData();
    formData.append('name', this.uploadForm.get("name")?.value);
    formData.append('description', this.uploadForm.get('description')?.value);
    formData.append('file', this.selectedFile);

    this.channelService.uploadVideo(formData);
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }
}
