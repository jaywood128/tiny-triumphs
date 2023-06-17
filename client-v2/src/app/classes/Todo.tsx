import { number } from 'prop-types';
import { Priority } from '../enums/Priority';
import User from './User';

export class Todo {
  constructor(
    private _name: string,
    private _description: string,
    private _isComplete: boolean,
    private _id?: number | undefined,
    private _dueDate?: Date | null,
    private _priority?: Priority,
    private _unixTimestamp?: number
  ) {
    /**
     * Optional fields
     */
    /**
     * In Typescript, if the modifier is given, the value does not need to be explicitly set
     */
    // this._name = _name;
    // this.description = _description;
    // this.isComplete = _isComplete;
    // if (_id) {
    //   this.id = _id;
    // }
    // if (_dueDate) {
    //   this.dueDate = _dueDate;
    // } else {
    //   this.dueDate = null;
    // }
    // if (_priority) {
    //   this.priority = _priority;
    // }
    // if (_unixTimestamp) {
    //   this.unixTimestamp = _unixTimestamp;
    // }
  }

  public get id(): number | undefined {
    return this._id;
  }
  public get name() {
    return this._name;
  }
  public get descripton() {
    return this._description;
  }
  public get isComplete() {
    return this._isComplete;
  }
  public get dueDate(): Date | undefined | null {
    return this._dueDate;
  }
  public get priority() {
    return this._priority;
  }
  public get unixTimestamp(): number | undefined {
    return this._unixTimestamp;
  }

  public set id(id: number | undefined) {
    this._id = id;
  }

  public set name(name: string) {
    this._name = name;
  }

  public set description(description: string) {
    this._description = description;
  }

  public set isComplete(isComplete: boolean) {
    this._isComplete = isComplete;
  }

  public set dueDate(dueDate: Date | undefined | null) {
    this._dueDate = dueDate;
  }

  public set priority(priority: Priority | undefined) {
    this._priority = priority;
  }

  public set unixTimestamp(unixTimestamp: number | undefined) {
    this._unixTimestamp = unixTimestamp;
  }

  toJSON() {
    return {
      name: this._name,
      description: this._description,
      isComplete: this._isComplete,
      id: this._id,
      dueDate: this._dueDate,
      priority: this._priority,
      unixTimestamp: this._unixTimestamp
    };
  }
}
