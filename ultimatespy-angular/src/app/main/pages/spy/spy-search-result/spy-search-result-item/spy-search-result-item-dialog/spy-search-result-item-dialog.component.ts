import { Component, ElementRef, Inject, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as moment from 'moment';
import { UserPostService } from 'src/app/core/services/user-post.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { PlanCtaDialogComponent } from 'src/app/shared/components/plan-cta-dialog/plan-cta-dialog.component';

declare var $: any;

@Component({
  selector: 'app-spy-search-result-item-dialog',
  templateUrl: './spy-search-result-item-dialog.component.html',
  styleUrls: ['./spy-search-result-item-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchResultItemDialogComponent implements OnInit {
  @ViewChild('dialogWrapper') dialogWrapper: ElementRef;

  Moment: any = moment;
  userId: string;

  // Chart options
  legend: boolean = true;
  legendPosition: string = 'below';
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = false;
  showXAxisLabel: boolean = false;
  xAxisLabel: string = 'Year';
  yAxisLabel: string = 'Population';
  timeline: boolean = true;
  view: any[] = [600, 300];
  autoScale = true;
  colorScheme = {
    domain: ['#E74C3C', '#17A589', '#F1C40F', '#3498DB', '#8E44AD']
  };

  constructor(
    public dialogRef: MatDialogRef<SpySearchResultItemDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private userPostService: UserPostService) {
    }

  ngOnInit(): void {
    $('[data-toggle="tooltip"]').tooltip();

    let user = localStorage.getItem('user');
    this.userId = user ? JSON.parse(user).id : '';
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dialogWrapper.nativeElement.scrollTop = 0;
    }, 0);
  }

  close(): void {
    this.dialogRef.close(this.data);
  }

  checkPermission(): boolean {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');

    // TODO: CHECK PLAN, IF SUBCRIBED, RETURN TRUE

    if (token && user) {
      const dialogRef = this.dialog.open(PlanCtaDialogComponent, {
        width: '540px',
        data: {}
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result && result === 'yes') {
        }
      });
    } else {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '440px',
        data: {
          title: '',
          message: 'You must have an account to do this. Do you want to redirect to sign up page?',
          yes: 'Ok',
          no: 'Cancel'
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result && result === 'yes') {
          window.location.href='/signup';
        }
      });
    }

    return false;
  }

  track(postId: string): void {
    if (this.checkPermission()) {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '440px',
        data: {
          title: 'Confirm',
          message: 'Are you sure want to add this post to your tracklist?'
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result && result === 'yes') {
          this.userPostService.insert(this.userId, postId, 'tracked').subscribe(
            data => {
              this.data.isTracked = true;
            },
            error => {
              console.log(error);
            }
          );
        }
      });
    }
  }

  unTrack(postId: string): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '440px',
      data: {
        title: 'Confirm',
        message: 'Are you sure want to remove this post from your tracklist?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result === 'yes') {
        this.userPostService.delete(this.userId, postId, 'tracked').subscribe(
          data => {
            this.data.isTracked = false;
          },
          error => {
            console.log(error);
          }
        );
      }
    });
  }

  save(postId: string): void {
    if (this.checkPermission()) {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '440px',
        data: {
          title: 'Confirm',
          message: 'Are you sure want to add this post to your saved list?'
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result && result === 'yes') {
          this.userPostService.insert(this.userId, postId, 'saved').subscribe(
            data => {
              this.data.isSaved = true;
            },
            error => {
              console.log(error);
            }
          );
        }
      });
    }
  }

  unSave(postId: string): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '440px',
      data: {
        title: 'Confirm',
        message: 'Are you sure want to remove this post to your saved list?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result === 'yes') {
        this.userPostService.delete(this.userId, postId, 'saved').subscribe(
          data => {
            this.data.isSaved = false;
          },
          error => {
            console.log(error);
          }
        );
      }
    });
  }

  onSelect(data): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

}
