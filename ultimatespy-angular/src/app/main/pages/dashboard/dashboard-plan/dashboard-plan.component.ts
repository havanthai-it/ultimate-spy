import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { PaymentService } from 'src/app/core/services/payment.service';

@Component({
  selector: 'app-dashboard-plan',
  templateUrl: './dashboard-plan.component.html',
  styleUrls: ['./dashboard-plan.component.scss']
})
export class DashboardPlanComponent implements OnInit {

  user: any = {};
  currentSubscription: any = {};
  payments: Array<any> = [];

  constructor(private userService : UserService, private paymentService : PaymentService) { }

  ngOnInit(): void {
    let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {};
    if (user && (user.id || user.email)) {
      this.userService.get(user.id, user.email).subscribe(
        data => {
          this.user = data;
          this.currentSubscription = (data.lstSubscriptions && data.lstSubscriptions.length > 0) ? data.lstSubscriptions[0] : {};
        },
        error => {
          console.log(error);
        }
      );

      this.paymentService.getByUser(user.id).subscribe(
        data => {
          this.payments = data;
        },
        error => {
          console.log(error);
        }
      );
    }
  }

}
