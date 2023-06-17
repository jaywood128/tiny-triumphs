'use client';
import React, { useEffect, useState } from 'react';
import { Priority } from '../enums/Priority';
import TodoComponent from '../components/TodoComponent';
import Head from 'next/head';
import { Props } from '../interfaces/Props';
import { useRouter } from 'next/router';
import User from '../classes/User';
import { Todo } from '../classes/Todo';
import { redirect } from 'next/navigation';
import { createContext, useContext } from 'react';
import { AuthenticationContext } from '../context/auth-provider';
import { TodoProps } from '../interfaces/TodoProps';
import Link from 'next/link';
import PopupModal from '../components/PopModal';
import { FormEvent } from 'react';

const TodosPage: React.FC<TodoProps> = (props: TodoProps) => {
  const { isAuthenticated, setIsAuthenticated } = useContext(
    AuthenticationContext
  );
  const [todos, setTodos] = useState<Todo[]>([]);
  const [todo, setTodo] = useState<Todo | undefined>(
    new Todo('', '', false, 0, null)
  );
  const [hovered, setHovered] = useState(false);
  const [hidePlusIcon, setHidePlusIcon] = useState(false);

  const handleNameChange = (
    e:
      | React.ChangeEvent<HTMLInputElement>
      | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { value, name } = e.target;
    console.log('Name ' + e.target.name);

    setTodo((prevState: Todo | undefined) => {
      if (prevState) {
        const updatedTodo = new Todo(
          value,
          prevState.descripton,
          prevState.isComplete,
          prevState?.id,
          prevState?.dueDate,
          prevState?.priority,
          prevState?.unixTimestamp
        );
        return updatedTodo;
      }
    });
  };

  const handleDueDateChange = (date: Date | null) => {
    setTodo((prevState: Todo | undefined) => {
      if (prevState) {
        const updatedTodo = new Todo(
          prevState.name,
          prevState.descripton,
          prevState.isComplete,
          prevState.id,
          date,
          prevState.priority,
          date?.getTime()
        );
        return updatedTodo;
      }
      return prevState;
    });
  };

  const handleDescriptionChange = (
    e:
      | React.ChangeEvent<HTMLInputElement>
      | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { value, name } = e.target;
    console.log('Name ' + e.target.name);

    setTodo((prevState: Todo | undefined) => {
      if (prevState) {
        const updatedTodo = new Todo(
          prevState.name,
          value,
          prevState.isComplete,
          prevState?.id,
          prevState?.dueDate,
          prevState?.priority,
          prevState?.unixTimestamp
        );
        return updatedTodo;
      }
    });
  };

  const handlePriorityChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    let { value, name } = e.target;
    /**
     * type guard Priority type
     */
    let priority: Priority | undefined;
    if (value === 'high') {
      priority = Priority.HIGH;
    } else if (value === 'medium') {
      priority = Priority.MEDIUM;
    } else if (value === 'low') {
      priority = Priority.LOW;
    }

    setTodo((prevState: Todo | undefined) => {
      if (prevState) {
        const updatedTodo = new Todo(
          prevState.name,
          prevState.description,
          prevState.isComplete,
          prevState?.id,
          prevState?.dueDate,
          priority,
          prevState?.unixTimestamp
        );
        return updatedTodo;
      }
    });
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_URL + '/todos', {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${window.localStorage.getItem('token')}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(todo?.toJSON())
      });
      if (!response.ok) {
        throw Error('Failed to create task');
      } else {
        const { data } = await response.json();
        console.log('Response from POST' + JSON.stringify(data));
        console.log('Added!');
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleIsComplete = (e: React.ChangeEvent<HTMLInputElement>) => {
    let { value, name } = e.target;
    let isComplete = value === 'true' ? true : false;

    setTodo((prevState: Todo | undefined) => {
      if (prevState) {
        const updatedTodo = new Todo(
          prevState.name,
          prevState.description,
          isComplete,
          prevState?.id,
          prevState?.dueDate,
          prevState?.priority,
          prevState?.unixTimestamp
        );
        return updatedTodo;
      }
    });
  };

  const handleHover = () => {
    setHovered(true);
  };

  const handleMouseLeave = () => {
    setHovered(false);
  };

  const handlePopupModalClick = () => {
    setHidePlusIcon(!hidePlusIcon);
  };

  const render = () => {
    if (todos.length > 0) {
      return (
        <div className="w-6/12 max-w-full h-auto bg-white rounded-lg shadow-md shadow-slate-600 font-normal">
          {/* <div className="flex"> */}
          <div className="flex justify-start w-2/10 h-auto p-6 space-x-5">
            <div>
              <Link
                className="text-xl active:border-solid active:border-b-2 active:border-customBlue visited:font-bold hover:border-solid hover:border-b-4 hover:border-customBlue hover:font-medium p-2"
                href="/tiny-triumphs"
              >
                Notifications
              </Link>
            </div>
            <div>
              <Link
                className="text-xl active:border-solid active:border-b-2 active:border-customBlue visited:font-bold hover:border-solid hover:border-b-4 hover:border-customBlue hover:font-medium p-2"
                href="/tiny-triumphs"
              >
                Todos
              </Link>
            </div>
          </div>
          <ul className="last:border-b-2 last:border-solid last:border-slate-200">
            {todos.map((todo: Todo | undefined) => (
              <li key={todo?.id} className="w-full h-auto">
                <TodoComponent todo={todo} />
              </li>
            ))}
          </ul>
          {hidePlusIcon ? (
            <></>
          ) : (
            <PopupModal
              handleSubmit={handleSubmit}
              todo={todo}
              handleNameChange={handleNameChange}
              handleDescriptionChange={handleDescriptionChange}
              handleDueDateChange={handleDueDateChange}
              handlePriorityChange={handlePriorityChange}
              handleHover={handleHover}
              handleMouseLeave={handleMouseLeave}
              hovered={hovered}
              onClick={handlePopupModalClick}
              handlePopupModalClick={handlePopupModalClick}
            />
          )}
        </div>
      );
    } else {
      return (
        <div className="w-6/12 max-w-full h-auto bg-white rounded-lg shadow-md shadow-slate-600 font-normal">
          {/* <div className="flex"> */}
          <div className="flex justify-start w-2/10 h-auto p-6 space-x-5">
            <div>
              <Link
                className="text-xl active:border-solid active:border-b-2 active:border-customBlue visited:font-bold hover:border-solid hover:border-b-4 hover:border-customBlue hover:font-medium p-2"
                href="/tiny-triumphs"
              >
                Notifications
              </Link>
            </div>
            <div>
              <Link
                className="text-xl active:border-solid active:border-b-2 active:border-customBlue visited:font-bold hover:border-solid hover:border-b-4 hover:border-customBlue hover:font-medium p-2"
                href="/tiny-triumphs"
              >
                Todos
              </Link>
            </div>
          </div>
          <PopupModal
            handleSubmit={handleSubmit}
            todo={todo}
            handleNameChange={handleNameChange}
            handleDescriptionChange={handleDescriptionChange}
            handleDueDateChange={handleDueDateChange}
            handlePriorityChange={handlePriorityChange}
            handleHover={handleHover}
            handleMouseLeave={handleMouseLeave}
            hovered={hovered}
            onClick={handlePopupModalClick}
            handlePopupModalClick={handlePopupModalClick}
          />
        </div>
      );
    }
  };

  useEffect(() => {
    const fetchTodos = async () => {
      try {
        const res = await fetch(process.env.NEXT_PUBLIC_API_URL + '/todos', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${window.localStorage.getItem('token')}`
          }
        });

        if (res.ok) {
          const data = await res.json();
          console.log(data);
          if (data.length === 0) {
            render();
          } else {
            setTodos(data);
          }
        }
      } catch (error) {
        // Handle error
        console.error(error);
      }
    };
    if (window.localStorage.getItem('token')) {
      fetchTodos();
    }
  }, [hidePlusIcon]);

  return (
    <div className="flex flex-col items-center justify-center w-full h-screen bg-stone-200 shadow-lg shaddow-slate-950">
      {render()}
    </div>
  );
};
export default TodosPage;
