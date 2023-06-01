import { Priority } from "../enums/Priority";
import User from './User';
 export class Todo {
  id?: number;
  name: string; 
  description: string;
  isComplete?: boolean;
  dueDate?: Date;
  priority?: Priority;

  constructor(id: number, name: string, description: string, isComplete: boolean, dueDate: Date,  priority: Priority
  
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.isComplete = isComplete;
    this.dueDate = dueDate;
    this.priority = priority;
  }
}

