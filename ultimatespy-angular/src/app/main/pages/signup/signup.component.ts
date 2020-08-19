import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SocialAuthService, GoogleLoginProvider } from 'angularx-social-login';
import { JwtService } from '../../../core/services/jwt.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SignupComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private jwtService: JwtService,
    private authService: SocialAuthService
    ) { }

  returnUrl: string;
  errorMessage: string;
  loading: boolean = false;
  rePasswordMatched: boolean = false;

  signupForm = new FormGroup({
    fullName: new FormControl(''),
    username: new FormControl(''),
    password: new FormControl(''),
    rePassword: new FormControl('')
  });

  user: any = {
    fuleName: '',
    username: '',
    password: '',
    rePassword: ''
  }

  ngOnInit(): void {
    // reset login status
    // this.jwtService.signout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  signup(): void {
    if (!this.user.fullName || !this.user.email || !this.user.password || !this.user.rePassword) {
      return;
    }
    this.loading = true;
    this.jwtService.authenticate(this.user).subscribe(
      data => {
        console.log(data);
        localStorage.setItem('jwtToken', data.jwtToken);
        this.loading = false;
        this.router.navigateByUrl(this.returnUrl);
      },
      error => {
        console.log(error);
        if (error.status === 401) {
          this.errorMessage = 'Email or password is incorrect!'
        } else {
          this.errorMessage = 'Sorry, something wrong!'
        }
        this.loading = false;
      }
    );
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)
      .then(user => {
        console.log(user);
      })
      .catch(error => {
        console.log(error);
      });
  }

  onChangeRePassword(): void {
    if (this.user.rePassword === this.user.password) {
      this.rePasswordMatched = true;
      this.signupForm.controls['rePassword'].setErrors(null);
    } else {
      this.rePasswordMatched = false;
      this.signupForm.controls['rePassword'].setErrors([{'REPASSWORD_NOT_MATCHED': ''}])
    }
  }

}
