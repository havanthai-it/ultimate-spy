import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/models/User';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-dashboard-profile',
  templateUrl: './dashboard-profile.component.html',
  styleUrls: ['./dashboard-profile.component.scss']
})
export class DashboardProfileComponent implements OnInit {

  constructor(private userService: UserService) { }

  isEditting: boolean = false;
  user: User = new User();

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  save(): void {
    this.userService.update(this.user).subscribe(
      data => {
        localStorage.setItem('user', JSON.stringify(this.user));
        this.isEditting = false;
        location.reload();
      },
      error => {
        console.log(error);
      }
    )
  }

}
