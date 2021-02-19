import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ForgotPasswordComponent implements OnInit {

  constructor(
    private userService: UserService
    ) { }

  email: string;
  errorMessage: string;
  loading: boolean = false;
  requestComplete: boolean = false;

  forgotPasswordForm = new FormGroup({
    email: new FormControl('', [Validators.required])
  });

  get f() {
    return this.forgotPasswordForm.controls;
  }

  ngOnInit(): void {
  }

  forgotPassword(): void {
    if (!this.email) {
      this.errorMessage = 'Please fill in all the information';
      return;
    }
    this.loading = true;
    this.userService.forgotPassword(this.email).subscribe(
      data => {
        this.loading = false;
        this.requestComplete = true;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
        this.loading = false;
      }
    );
  }

}
