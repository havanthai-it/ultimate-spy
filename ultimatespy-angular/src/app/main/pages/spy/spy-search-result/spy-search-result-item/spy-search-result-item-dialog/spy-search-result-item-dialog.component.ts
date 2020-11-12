import { Component, ElementRef, Inject, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as moment from 'moment';

@Component({
  selector: 'app-spy-search-result-item-dialog',
  templateUrl: './spy-search-result-item-dialog.component.html',
  styleUrls: ['./spy-search-result-item-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchResultItemDialogComponent implements OnInit {
  @ViewChild('dialogWrapper') dialogWrapper: ElementRef;

  Moment: any = moment;

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
  colorScheme = {
    domain: ['#E74C3C', '#17A589', '#F1C40F', '#3498DB', '#8E44AD']
  };

  constructor(
    public dialogRef: MatDialogRef<SpySearchResultItemDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dialogWrapper.nativeElement.scrollTop = 0;
    }, 0);
  }

  close(): void {
    this.dialogRef.close();
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
