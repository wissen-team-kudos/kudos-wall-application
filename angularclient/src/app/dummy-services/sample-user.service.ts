import { Injectable } from '@angular/core';
import { User} from '../models/user'

@Injectable({
  providedIn: 'root'
})
export class SampleUserService {

  user: User;

  constructor() { 

	this.user={"id": 1,
        "username": "user1",
        "password": "pass1",
        "kudos": [
            {
                "id": 1,
                "content": "kudos4",
                "author": {
                    "id": 2,
                    "username": "user2",
                    "password": "pass2"
                }
            },
            {
                "id": 2,
                "content": "kudos2",
                "author": {
                    "id": 2,
                    "username": "user2",
                    "password": "pass2"
                }
            },
            {
                "id": 3,
                "content": "kudos3",
                "author": {
                    "id": 3,
                    "username": "user3",
                    "password": "pass3"
                }
            }
        ],
        "groups": [
            {
                "id": 1,
                "groupname": "group1",
                "password": "group1"
            },
            {
                "id": 2,
                "groupname": "group2",
                "password": "group2"
            }
        ]
    }
  }

  getUser(){
    return this.user;
  }
  
}
