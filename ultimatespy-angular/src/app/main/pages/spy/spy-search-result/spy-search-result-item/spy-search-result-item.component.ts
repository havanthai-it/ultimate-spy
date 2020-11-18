import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpySearchResultItemDialogComponent } from './spy-search-result-item-dialog/spy-search-result-item-dialog.component';
import * as moment from 'moment';
import { PostService } from 'src/app/core/services/post.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { UserPostService } from 'src/app/core/services/user-post.service';
import { PlanCtaDialogComponent } from 'src/app/shared/components/plan-cta-dialog/plan-cta-dialog.component';

declare var $:any;
@Component({
  selector: 'app-spy-search-result-item',
  templateUrl: './spy-search-result-item.component.html',
  styleUrls: ['./spy-search-result-item.component.scss']
})
export class SpySearchResultItemComponent implements OnInit, OnChanges {
  @Input() post: any;
  @Input() isSaved: boolean;
  @Input() isTracked: boolean;
  @Input() isSearching: boolean = false;

  Moment: any = moment;
  userId: string;

  constructor(
    private dialog: MatDialog,
    private postService: PostService,
    private userPostService: UserPostService) { }

  ngOnInit(): void {
    $('[data-toggle="tooltip"]').tooltip();

    let user = localStorage.getItem('user');
    this.userId = user ? JSON.parse(user).id : '';
  }

  ngOnChanges(): void {
  }

  detail(): void {
    this.postService.getFacebookPost(this.post.postId).subscribe(
      data => {
        data.isSaved = this.isSaved;
        data.isTracked = this.isTracked;
        const dialogRef = this.dialog.open(SpySearchResultItemDialogComponent, {
          width: '1024px',
          data: data
        });

        dialogRef.afterClosed().subscribe(result => {
          if (!result) {
            result = dialogRef.componentInstance.data;
          }
          this.isSaved = result.isSaved;
          this.isTracked = result.isTracked;
        });
      },
      error => {
        console.log(error);
      }
    );
  }

  extractContent(htmlString: string): string {
    var span = document.createElement('span');
    span.innerHTML = htmlString;
    return span.textContent || span.innerText;
  };

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
              this.isTracked = true;
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
            this.isTracked = false;
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
              this.isSaved = true;
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
            this.isSaved = false;
          },
          error => {
            console.log(error);
          }
        );
      }
    });
  }

}
