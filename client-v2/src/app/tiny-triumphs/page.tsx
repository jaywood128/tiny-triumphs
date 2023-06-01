"use client";
import React, { useEffect, useState } from 'react';
import { Priority } from '../enums/Priority';
import TodoComponent from '../components/TodoComponent';
import Head from 'next/head';
import { Props } from '../interfaces/Props';
import { useRouter } from "next/router";
import User from '../classes/User';
import { Todo } from '../classes/Todo';
import { redirect } from 'next/navigation'
import { createContext, useContext } from "react";
import { AuthenticationContext } from '../context/auth-provider';


const  TodosPage : React.FC = () =>  {
  const { isAuthenticated, setIsAuthenticated } = useContext(AuthenticationContext);
  const [todos, setTodos] = useState<Todo[]>([]);
  const [todo, setTodo] = useState<Todo>({name: "", description: "", isComplete: false});
  let todo1 : Todo = {
    name: "Finish Succession finale",
    description: "Watch finale to avoid spoilers",
    isComplete: false,
    dueDate : new Date(), 
    priority: Priority.HIGH
  
  }

   
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>  | React.ChangeEvent<HTMLTextAreaElement>)  => {
    const { value, name } = e.target;
    console.log("Name " + e.target.name)

    setTodo((prevState : Todo) => ({
      ...prevState,
      [name] : value
    }));
    

  }

  const handleTextareaChange: React.ChangeEventHandler<HTMLTextAreaElement> = (event : React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;

    setTodo((prevState: Todo) => ({
      ...prevState,
      description: value,
    }))
    
  }

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const response = await fetch(process.env.NEXTAUTH_URL + "/todos", {
        method: 'POST',
        headers: {
          Authorization : `Bearer ${window.localStorage.getItem('token')}`
        }
      });
      if (!response.ok) {
        throw Error('Failed to create task');
      } else {
        const {data}  = await response.json();
        console.log("Response from POST" + JSON.stringify(data));
        console.log("Added!")
      }
    } catch (error) {
      console.error(error);
    }
  
  };

  const render = () => {
    // if(todos.length === 0 ){
    //   return (
    //     <div>
    //     Take your first step to kicking tomorrow's ass...
    //     {/* <button onClick={handleClick}>Add Task</button> */}
    //   </div>
    //   )
    // } else {
      return (

        //  <ul>
        //   {todos.map((todo: Todo) => (
        //     <li>
        //       <TodoComponent
        //         key={todo.id}
        //         id={todo.id}
        //         name={todo.name}
        //         description={todo.description}
        //         isComplete={todo.isComplete}
        //         dueDate={todo.dueDate}
        //         priority={todo.priority}
        //       />
        //     </li>
        //   ))}
        // </ul> 
        
            <div>
              <form onSubmit={handleSubmit} >
                <div className="container mx-auto p-4">
                  <div className="flex flex-col items-center">
                  <input type="text" id="name" name="name" className="p-2 border-2 border-gray-300 rounded-lg" placeholder="Task name" required value={todo?.name || ""} onChange={handleChange}/>
                    <textarea id="taskDescription" name="description" className="flex-grow h-32 p-2 border-2 border-gray-300 rounded-lg" onChange={(handleChange)}
                      placeholder="Task description"></textarea>
                  </div>
                </div>
              </form>
            </div>

      )
  }



  useEffect(()=> {

  const fetchTodos = async () => {

    try {
      const res = await fetch(process.env.NEXT_PUBLIC_API_URL + "/todos", {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${window.localStorage.getItem('token')}`
        }
      });

      if(res.ok){
        const data = await res.json();
        console.log(data);
        if(data.length === 0){
          render()
        } else {
          setTodos(data);
        }
      }
    } catch (error) {
      // Handle error
      console.error(error);
    }

  }
  if(window.localStorage.getItem('token')){
    fetchTodos();
  }

  }, [todos])

  return (
    <div className='flex justify-center my-8 mx-8'>
      {render()}
    </div>
  );
}
export default TodosPage;
