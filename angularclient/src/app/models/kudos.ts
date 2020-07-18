export interface Kudos{
	id?:number,
	content:string,
	author:{
            id: number,
            username: string,
            password: string
        	},
    users:{
        	id:number,
            username: string,
            password: string
            }[],

	groups:{ 
            id:number,
            groupname: string,
            password: string
    		}[]
    }

