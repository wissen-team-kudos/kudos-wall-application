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
    groups? : {
      id : number,
      groupname : string,
      password: string
    }[] | null
  }