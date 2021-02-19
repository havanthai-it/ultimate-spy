import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent implements OnInit {

  confirmId: string;
  userId: string;

  constructor(private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.confirmId = params.confirmId;
      this.userId = atob(params.confirmId);
      this.userService.confirm(this.userId, this.confirmId).subscribe(
        data => {
          alert('Your email has been confirmed!');
          window.location.href = '/signin';
        },
        error => {
          console.log(error);
        }
      );
    });
  }

}
