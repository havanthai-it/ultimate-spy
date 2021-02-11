import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SubscriberEmailService } from 'src/app/core/services/subscriber-email.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  email: string;
  year: number = new Date().getFullYear();
  constructor(private dialog: MatDialog, private subscriberEmailService: SubscriberEmailService) { }

  ngOnInit(): void {
  }

  scrollToView(id: string): void {
    let eleRef = document.getElementById(id);
    window.scrollTo(eleRef.offsetLeft, eleRef.offsetTop);
  }

  subscribe(): void {
    if (this.email) {
      this.subscriberEmailService.subscribe(this.email).subscribe(
        data => {
          const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: '440px',
            data: {
              title: '',
              message: 'Thank you for subscribing to newsletter of AdsCrawlr.com',
              yes: 'Ok',
              no: 'Cancel'
            }
          });
          dialogRef.afterClosed().subscribe(result => {
            if (result && result === 'yes') {
              window.location.href='/signup';
            }
          });
        },
        error => {
          console.log(error);
        }
      );
    }
  }

}
