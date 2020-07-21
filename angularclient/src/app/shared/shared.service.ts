import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Room } from '../models/room';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  roomAdded  = new Subject<Room>();
}
