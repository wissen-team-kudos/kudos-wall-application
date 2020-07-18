import { Component, OnInit, Input } from '@angular/core';
import { Kudos } from '../models/kudos';
@Component({
  selector: 'kudo-card',
  templateUrl: './kudo-card.component.html',
  styleUrls: ['./kudo-card.component.css']
})
export class KudoCardComponent implements OnInit {

	@Input() theKudo:Kudos;

	constructor() { }

	ngOnInit(): void {
  }

}
