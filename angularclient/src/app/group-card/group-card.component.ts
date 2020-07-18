import { Component, OnInit, Input } from '@angular/core';
import { Group } from '../models/group';

@Component({
  selector: 'group-card',
  templateUrl: './group-card.component.html',
  styleUrls: ['./group-card.component.css']
})
export class GroupCardComponent implements OnInit {

  @Input() theGroup : Group;

  constructor() { }

  ngOnInit(): void {
  }

  onShare(groupname: String){
    alert("Sharing "+groupname);
  }

}
