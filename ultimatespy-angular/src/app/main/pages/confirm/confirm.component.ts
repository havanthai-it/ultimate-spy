import { Component, OnInit } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';
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

  constructor(private route: ActivatedRoute,
    private titleService: Title,
    private metaService: Meta,
    private userService: UserService) {
      this.titleService.setTitle('AdsCrawlr | Confirm');
    }

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
