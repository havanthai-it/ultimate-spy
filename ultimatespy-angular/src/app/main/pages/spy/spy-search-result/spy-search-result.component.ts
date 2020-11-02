import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';

@Component({
  selector: 'app-spy-search-result',
  templateUrl: './spy-search-result.component.html',
  styleUrls: ['./spy-search-result.component.scss']
})
export class SpySearchResultComponent implements OnInit {
  @Input() data: any = {};
  currentPage: number;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.currentPage = params.page ? parseInt(params.page) : 1;
      console.log(this.currentPage);
    });
  }

  ngOnChanges(): void {
    console.log('ngOnChanges: ' + this.data);
  }

  /**
   * i from 0 -> total - 1
   */
  showPage(i: number): boolean {
    if (this.data.pages <= 6) return true;
    if (i === 0 || i === (this.data.pages -  1)) return true;
    if ((i + 1) > (this.currentPage - 2) && (i + 1) < (this.currentPage + 2)) return true;
    if (this.currentPage === 1 && (i + 1) < 4) return true;
    return false;
  }

  showPageEllipsis(i: number): boolean {
    if (this.data.pages > 6 && this.currentPage === 1 && (i + 1) === 3) return false;
    if (this.data.pages > 6 && this.currentPage === 1 && (i + 1) === 4) return true;
    if (this.data.pages > 6 && i > 0 && i < this.data.pages - 1 && ((i + 1) === this.currentPage - 2 || (i + 1) === this.currentPage + 2)) return true;
    return false;
  }
}
