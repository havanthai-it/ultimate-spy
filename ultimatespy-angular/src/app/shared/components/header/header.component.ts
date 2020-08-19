import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  lstLanguages: any[] = [
    {
      label: 'English',
      value: 'EN'
    },
    {
      label: 'France',
      value: 'FR'
    },
    {
      label: 'Vietnamese',
      value: 'VI'
    }
  ];
  selectedLang: string = this.lstLanguages[0].value;

  ngOnInit(): void {
  }

}
