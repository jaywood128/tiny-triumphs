import { Todo } from '../classes/Todo';
import { ReactNode } from 'react';
import { TodoProps } from '../interfaces/TodoProps';
import CircleIcon from './CircleIcon';

export default function TodoComponent(todoProps: TodoProps | undefined) {
  const todo = todoProps?.todo;
  const id = todo?.id;
  const name = todo?.name;
  const description = todo?.description;
  const isComplete = todo?.isComplete;
  const dueDate = todo?.dueDate;
  const priority = todo?.priority;

  return (
    <div className="flex w-full h-auto overflow-hidden my-1 border-slate-200 border-t-2 border-solid">
      <div className="px-6 py-1">
        <div className="px-6 pt-4 pb-2">
          <div className="flex">
            <CircleIcon />
            <div className="flex flex-col ml-6">
              <p className="font-medium text-lg">{name}</p>
              <p className="text-slate-500">{description}</p>
              <div className="mr-2"></div>
              {dueDate ? (
                <div>
                  <span className="text-gray-600 font-semibold">
                    Due Date:{' '}
                  </span>
                  <span>{dueDate?.toString()}</span>
                </div>
              ) : (
                ''
              )}

              {priority ? (
                <div className="text-gray-600 font-semibold">
                  Priority: {priority}
                </div>
              ) : (
                ''
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
