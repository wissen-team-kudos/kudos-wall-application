import { Injectable } from '@angular/core';
import { Kudos} from '../models/kudos'

@Injectable({
  providedIn: 'root'
})
export class SampleKudoService {

  kudos: Kudos[];

  constructor() { 
    this.kudos=[
    {
        "id": 2,
        "content": "kudos2",
        "author": {
            "id": 2,
            "username": "user2",
            "password": "pass2"
        },
        "users": [
            {
                "id": 1,
                "username": "user1",
                "password": "pass1"
            },
            {
                "id": 3,
                "username": "user3",
                "password": "pass3"
            }
        ],
        "groups": [
            {
                "id": 1,
                "groupname": "group1",
                "password": "group1"
            }
        ]
    },
    {
        "id": 3,
        "content": "kudos3",
        "author": {
            "id": 3,
            "username": "user3",
            "password": "pass3"
        },
        "users": [
            {
                "id": 1,
                "username": "user1",
                "password": "pass1"
            }
        ],
        "groups": [
            {
                "id": 2,
                "groupname": "group2",
                "password": "group2"
            }
        ]
    },
    {
        "id": 1,
        "content": "kudos4",
        "author": {
            "id": 2,
            "username": "user2",
            "password": "pass2"
        },
        "users": [
            {
                "id": 1,
                "username": "user1",
                "password": "pass1"
            }
        ],
        "groups": []
    }
];

  }

  getKudos(){
    return this.kudos;
  }
  
  addKudo(kudo: Kudos){
    this.kudos.push(kudo);
    console.log(this.kudos);
  }
}
