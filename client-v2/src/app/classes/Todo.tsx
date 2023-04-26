import { Priority } from "../enums/Priority";
import User from './User';
export class Todo {
  constructor(
    public id: number,
    public user: User,
    public description: string,
    public isComplete: boolean,
    public dueDate: string,
    public priority: Priority
  ) {}
}
