import { Component, ElementRef, Inject, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Meta, Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { PostService } from 'src/app/core/services/post.service';
import { UserPostService } from 'src/app/core/services/user-post.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { PlanCtaDialogComponent } from 'src/app/shared/components/plan-cta-dialog/plan-cta-dialog.component';

declare var $: any;

@Component({
  selector: 'app-spy-ads-detail',
  templateUrl: './spy-ads-detail.component.html',
  styleUrls: ['./spy-ads-detail.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpyAdsDetailComponent implements OnInit {
  @ViewChild('dialogWrapper') dialogWrapper: ElementRef;

  Moment: any = moment;
  userId: string;
  id: string;
  data: any;

  // Chart options
  legend: boolean = true;
  legendPosition: string = 'below';
  showLabels: boolean = true;
  animations: boolean = false;
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
    private titleService: Title, 
    private metaService: Meta,
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
    private userPostService: UserPostService,
    private postService: PostService) {
      this.titleService.setTitle('AdsCrawlr | Ads Detail');
      this.metaService.addTags([
        { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
        { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'robots', content: 'index, follow' },
        { name: 'og:title', content: 'AdsCrawlr | Ads Detail'},
        { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
        { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'og:url', content: 'https://adscrawlr.com/' }
      ]);
    }

  ngOnInit(): void {
    $('[data-toggle="tooltip"]').tooltip();

    let user = localStorage.getItem('user');
    this.userId = user ? JSON.parse(user).id : '';

    this.id = this.activatedRoute.snapshot.params['id'];
    this.loadAdsDetail(this.id);
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dialogWrapper.nativeElement.scrollTop = 0;
    }, 0);
  }

  yAxisTickFormat(val: number): string {
    if (val % 1 === 0) {
      return val.toLocaleString();
    } else {
      return '';
    }
  }

  loadAdsDetail(id: string): void {
    this.postService.getFacebookPost(id).subscribe(
      data => {
        this.data = data;
      },
      error => {}
    );
  }

  checkPermission(): boolean {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');

    if (token && user) {
      let userJson = JSON.parse(user);
      if (userJson.plan && userJson.plan !== 'free') {
        return true;
      }

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
              this.data.tracked = true;
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
            this.data.tracked = false;
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
              this.data.saved = true;
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
            this.data.saved = false;
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

  bindPlatformLogo(platform: string): string {
    if (platform === 'amazon') {
      return '../../../../../../../assets/img/platforms/amazon.png';
    } else if (platform === 'bigcommerce') {
      return '../../../../../../../assets/img/platforms/bigcommerce.png';
    } else if (platform === 'ebay') {
      return '../../../../../../../assets/img/platforms/ebay.png';
    } else if (platform === 'etsy') {
      return '../../../../../../../assets/img/platforms/etsy.png';
    } else if (platform === 'gearbubble') {
      return '../../../../../../../assets/img/platforms/gearbubble.png';
    } else if (platform === 'gearlaunch') {
      return '../../../../../../../assets/img/platforms/gearlaunch.png';
    } else if (platform === 'hostingrocket') {
      return '../../../../../../../assets/img/platforms/hostingrocket.png';
    } else if (platform === 'magento') {
      return '../../../../../../../assets/img/platforms/magento.png';
    } else if (platform === 'merchize') {
      return '../../../../../../../assets/img/platforms/merchize.png';
    } else if (platform === 'merchking') {
      return '../../../../../../../assets/img/platforms/merchking.png';
    } else if (platform === 'redbubble') {
      return '../../../../../../../assets/img/platforms/redbubble.png';
    } else if (platform === 'shopbase') {
      return '../../../../../../../assets/img/platforms/shopbase.png';
    } else if (platform === 'shopify') {
      return '../../../../../../../assets/img/platforms/shopify.png';
    } else if (platform === 'spreadshirt') {
      return '../../../../../../../assets/img/platforms/spreadshirt.png';
    } else if (platform === 'sunfrog') {
      return '../../../../../../../assets/img/platforms/sunfrog.png';
    } else if (platform === 'teechip') {
      return '../../../../../../../assets/img/platforms/teechip.png';
    } else if (platform === 'teehag') {
      return '../../../../../../../assets/img/platforms/teehag.png';
    } else if (platform === 'teemill') {
      return '../../../../../../../assets/img/platforms/teemill.png';
    } else if (platform === 'teepublic') {
      return '../../../../../../../assets/img/platforms/teepublic.png';
    } else if (platform === 'teespring') {
      return '../../../../../../../assets/img/platforms/teespring.png';
    } else if (platform === 'teezily') {
      return '../../../../../../../assets/img/platforms/teezily.png';
    } else if (platform === 'woocommerce') {
      return '../../../../../../../assets/img/platforms/woocommerce.png';
    } else {
      return '../../../../../../../assets/img/platforms/_default.png';
    }
  }

}

