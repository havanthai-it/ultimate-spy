import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpySearchResultItemDialogComponent } from './spy-search-result-item-dialog/spy-search-result-item-dialog.component';
import * as moment from 'moment';
import { SpyService } from 'src/app/core/services/spy.service';

@Component({
  selector: 'app-spy-search-result-item',
  templateUrl: './spy-search-result-item.component.html',
  styleUrls: ['./spy-search-result-item.component.scss']
})
export class SpySearchResultItemComponent implements OnInit, OnChanges {
  @Input() post: any;
  @Input() isSearching: boolean = false;

  Moment: any = moment;

  constructor(private dialog: MatDialog, private spyService: SpyService) { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
  }

  detail(): void {
    this.spyService.getFacebookPost(this.post.postId).subscribe(
      data => {
        this.dialog.open(SpySearchResultItemDialogComponent, {
          width: '1000px',
          data: data
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

}
