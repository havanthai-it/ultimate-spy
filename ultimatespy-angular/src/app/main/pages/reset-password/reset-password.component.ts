import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ResetPasswordComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
    ) { }

  resetId: string;
  email: string;
  password: string;
  confirmPassword: string;
  responseMessage: string;
  errorMessage: string;
  loading: boolean = false;
  confirmPasswordMatched: boolean = false;

  resetForm = new FormGroup({
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required])
  });

  get f() {
    return this.resetForm.controls;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.resetId = params.resetId;
      this.email = atob(params.resetId);
    });
  }

  reset(): void {
    if (!this.password || !this.confirmPassword) {
      this.errorMessage = 'Please fill in all the information';
      return;
    }
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Confirm password does not match';
      return;
    }
    this.loading = true;
    this.userService.reset(this.email, btoa(this.password)).subscribe(
      data => {
        this.responseMessage = 'Your password has been reset. <br>Redirecting to sign in page...';
        this.loading = false;
        setTimeout(() => {
          window.location.href = '/signin';
        }, 3000);
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
        this.loading = false;
      }
    );
  }

  onChangeConfirmPassword(): void {
    if (this.confirmPassword === this.password) {
      this.confirmPasswordMatched = true;
      this.resetForm.controls['confirmPassword'].setErrors(null);
    } else {
      this.confirmPasswordMatched = false;
      this.resetForm.controls['confirmPassword'].setErrors([{'CONFIRM_PASSWORD_NOT_MATCHED': ''}])
    }
  }

}
