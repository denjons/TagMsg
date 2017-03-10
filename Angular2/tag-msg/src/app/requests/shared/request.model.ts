
export class Request{
    id: string;
    content: string;
    date: Date;
    tags: String[];

    constructor(id: string, content: string, date: Date, tags:string[]){
        this.id = id;
        this.content = content;
        this.date = date;
        this.tags = tags;
    }

}