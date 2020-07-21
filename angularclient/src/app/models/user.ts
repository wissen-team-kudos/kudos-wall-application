export interface User{
    id? : number,
    username : string,
    password : string,
    kudos? : {
      id : number,
      content : string,
      author : {
        id : number,
        username : string,
        password : string
      }
    }[] | null,
    rooms? : {
      id : number,
      roomname : string,
      password: string
    }[] | null
  }