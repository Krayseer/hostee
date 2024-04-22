import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/UserService";

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrl: './authorization.component.css'
})
export class AuthorizationComponent implements OnInit {

  userForm!: FormGroup

  constructor(private fb: FormBuilder,
              private userService: UserService) {
  }

  ngOnInit() {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    const userData = JSON.stringify(this.userForm.value);
    this.userService.authenticateUser(userData);
  }

}
