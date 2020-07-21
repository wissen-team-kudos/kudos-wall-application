export interface Room {
    id? : number,
    roomname : string,
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
    users? : {
        id : number,
        username : string,
        password: string,
      }[] | null
}
