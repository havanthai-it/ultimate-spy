import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  email: string;
  constructor() { }

  ngOnInit(): void {
  }

  scrollToView(id: string): void {
    let eleRef = document.getElementById(id);
    window.scrollTo(eleRef.offsetLeft, eleRef.offsetTop);
  }

}
