import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Group } from '../models/group';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  groupAdded  = new Subject<Group>();
}
