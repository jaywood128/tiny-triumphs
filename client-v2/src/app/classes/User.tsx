import { Todo } from "./Todo";
export default class User {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public todos: Todo[]
  ) {}
}
