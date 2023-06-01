'use client';
import { useState } from 'react';
import register from '../../api/register/route'; // Assuming an API module for registering users
import { useEffect, useContext } from "react";
import { AuthenticationContext } from '@/app/context/auth-provider';
import { Props } from "./../../interfaces/Props"
import { redirect } from 'next/navigation'
import { RegistrationData } from '@/app/interfaces/RegistrationData';
import { RegistrationResponse } from '@/app/interfaces/RegistrationResponse';

const Register : React.FC<Props> = (props) => {

  const {isAuthenticated, setIsAuthenticated} = useContext(AuthenticationContext)


  const [formData, setFormData] = useState<RegistrationData>({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });

  const [registrationResponse, setRegistrationResponse] = useState<RegistrationResponse>({
    token: "",
    refreshToken: ""
  })

    

  useEffect(() => {
    if (isAuthenticated) {
      redirect('/tiny-triumphs');
    }

  }, [registrationResponse]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const clearInvalidToken = () => {
    localStorage.removeItem("token");
  }

  const handleFirstNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setFormData({
      ...formData,
     firstName: value
    });
    props.onFirstNameChange(value)
  }

  const handleLastNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setFormData({
      ...formData,
     lastName: value
    });
    props.onLastNameChange(value)
  }

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setFormData({
      ...formData,
     email: value
    });
    props.onEmailChange(value)
  }

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setFormData({
      ...formData,
     password: value
    });
    props.onPasswordChange(value)
  }

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formDataInstance = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      email: formData.email,
      password: formData.password,
    };
    try {
      const response = await register(formDataInstance);
      if (!response.ok) {
        throw Error('Failed to register');
      } else {
        const {data}  = await response.json();
        console.log("Response from register" + JSON.stringify(data.token));
        setRegistrationResponse({ token : data.token, refreshToken: data.refreshToken}) 
        setIsAuthenticated(true);
        clearInvalidToken();
        localStorage.setItem('token', data.token)
        localStorage.setItem('refreshToken', data.refreshToken)
      }
    } catch (error) {
      console.error(error);
    }
  
  };

  return (
    <div>
        <div className='flex justify-center items-center h-screen bg-gray-900 text-white'>
          <form
            onSubmit={handleSubmit}
            className='bg-gray-800 p-8 rounded-lg shadow-md w-full md:w-96'
          >
            <div className='grid gap-4'>
              <input
                required
                id='firstName'
                name='firstName'
                type='text'
                value={formData.firstName}
                onChange={handleFirstNameChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='First Name'
              />
              <input
                required
                id='lastName'
                name='lastName'
                type='text'
                value={formData.lastName}
                onChange={handleLastNameChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='Last Name'
              />
              <input
                required
                id='email'
                name='email'
                type='email'
                value={formData.email}
                onChange={handleEmailChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='Email'
              />
              <input
                required
                id='password'
                name='password'
                type='password'
                value={formData.password}
                onChange={handlePasswordChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='Password'
              />
            </div>
            <button
              type='submit'
              className='mt-6 py-2 px-4 bg-blue-500 text-white rounded-lg hover:bg-blue-600'
            >
              Sign Up
            </button>
          </form>
        </div>
    </div>
  );
};

export default Register;
