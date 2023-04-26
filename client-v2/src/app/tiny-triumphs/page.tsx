import React from 'react';
import { Priority } from '../enums/Priority';
import TodoComponent from '../components/TodoComponent';
import Head from 'next/head';

function todoIndexPage() {
  const createTodos = () => {
    // Replace this with your actual implementation to fetch todos from a data source
    // For this example, we'll just return a hardcoded array of todos

    const user: User = {
      id: 1,
      firstName: 'Ted',
      lastName: 'Lasso',
      email: 'tedlasso@example.com',
      password: 'mypassword',
      todos: [],
    };
    const todos: Todo[] = [
      {
        id: 1,
        user: user,
        description:
          'Lead team building exercises. I want to see what happens when this gets much longer and verbose. How much should this container grow to adjust to the container?',
        isComplete: false,
        dueDate: '2023-04-30',
        priority: Priority.HIGH,
      },
      {
        id: 2,
        user: user,
        description: 'Inspire team with motivational speech',
        isComplete: true,
        dueDate: '2023-04-25',
        priority: Priority.MEDIUM,
      },
      {
        id: 3,
        user: user,
        description: "Learn about players' personal lives and interests",
        isComplete: false,
        dueDate: '2023-04-22',
        priority: Priority.LOW,
      },
      {
        id: 4,
        user: user,
        description: 'Develop unique coaching strategies',
        isComplete: false,
        dueDate: '2023-04-30',
        priority: Priority.HIGH,
      },
      {
        id: 5,
        user: user,
        description: 'Support team members through personal challenges',
        isComplete: false,
        dueDate: '2023-04-25',
        priority: Priority.MEDIUM,
      },
      {
        id: 6,
        user: user,
        description: 'Bring in biscuits for team meetings',
        isComplete: true,
        dueDate: '2023-04-22',
        priority: Priority.LOW,
      },
    ];

    return todos;
  };

  const todos = createTodos();
  return (
    <div className='flex justify-center my-8 mx-8'>
      <ul>
        {todos.map((todo) => (
          <li>
            <TodoComponent
              key={todo.id}
              id={todo.id}
              user={todo.user}
              description={todo.description}
              isComplete={todo.isComplete}
              dueDate={todo.dueDate}
              priority={todo.priority}
            />
          </li>
        ))}
      </ul>
    </div>
  );
}
export default todoIndexPage;
