import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpySearchResultItemDialogComponent } from './spy-search-result-item-dialog/spy-search-result-item-dialog.component';
import * as moment from 'moment';
import { PostService } from 'src/app/core/services/post.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { UserPostService } from 'src/app/core/services/user-post.service';
import { PlanCtaDialogComponent } from 'src/app/shared/components/plan-cta-dialog/plan-cta-dialog.component';

declare var $: any;
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
    this.post.videos = this.post.videos.split(',')[0];
    if (this.post.videos && this.post.videos.substr(0, 5) === 'blob:') {
      this.post.videos = encodeURI(this.post.videos.substr(5));
    }
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

  bindPlatformLogo(platform: string): string {
    if (platform === 'amazon') {
      return '../../../../../../assets/img/platforms/amazon.png';
    } else if (platform === 'bigcommerce') {
      return '../../../../../../assets/img/platforms/bigcommerce.png';
    } else if (platform === 'ebay') {
      return '../../../../../../assets/img/platforms/ebay.png';
    } else if (platform === 'etsy') {
      return '../../../../../../assets/img/platforms/etsy.png';
    } else if (platform === 'gearbubble') {
      return '../../../../../../assets/img/platforms/gearbubble.png';
    } else if (platform === 'gearlaunch') {
      return '../../../../../../assets/img/platforms/gearlaunch.png';
    } else if (platform === 'hostingrocket') {
      return '../../../../../../assets/img/platforms/hostingrocket.png';
    } else if (platform === 'magento') {
      return '../../../../../../assets/img/platforms/magento.png';
    } else if (platform === 'merchize') {
      return '../../../../../../assets/img/platforms/merchize.png';
    } else if (platform === 'merchking') {
      return '../../../../../../assets/img/platforms/merchking.png';
    } else if (platform === 'redbubble') {
      return '../../../../../../assets/img/platforms/redbubble.png';
    } else if (platform === 'shopbase') {
      return '../../../../../../assets/img/platforms/shopbase.png';
    } else if (platform === 'shopify') {
      return '../../../../../../assets/img/platforms/shopify.png';
    } else if (platform === 'spreadshirt') {
      return '../../../../../../assets/img/platforms/spreadshirt.png';
    } else if (platform === 'sunfrog') {
      return '../../../../../../assets/img/platforms/sunfrog.png';
    } else if (platform === 'teechip') {
      return '../../../../../../assets/img/platforms/teechip.png';
    } else if (platform === 'teehag') {
      return '../../../../../../assets/img/platforms/teehag.png';
    } else if (platform === 'teemill') {
      return '../../../../../../assets/img/platforms/teemill.png';
    } else if (platform === 'teepublic') {
      return '../../../../../../assets/img/platforms/teepublic.png';
    } else if (platform === 'teespring') {
      return '../../../../../../assets/img/platforms/teespring.png';
    } else if (platform === 'teezily') {
      return '../../../../../../assets/img/platforms/teezily.png';
    } else if (platform === 'woocommerce') {
      return '../../../../../../assets/img/platforms/woocommerce.png';
    } else {
      return '../../../../../../assets/img/platforms/_default.png';
    }
    
  }

}
