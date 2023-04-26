'use client';
import { useState } from 'react';
import register from '../../api/register/route'; // Assuming an API module for registering users

const Register = () => {
  const [formData, setFormData] = useState<RegistrationData>({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

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
      // Handle success or error response
      console.log('Response:', response);
      if (!response.ok) {
        throw Error('Failed to register ');
      } else {
        return response.json();
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
                onChange={handleChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='First Name'
              />
              <input
                required
                id='lastName'
                name='lastName'
                type='text'
                value={formData.lastName}
                onChange={handleChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='Last Name'
              />
              <input
                required
                id='email'
                name='email'
                type='email'
                value={formData.email}
                onChange={handleChange}
                className='bg-gray-700 text-white py-2 px-4 rounded-lg w-full'
                placeholder='Email'
              />
              <input
                required
                id='password'
                name='password'
                type='password'
                value={formData.password}
                onChange={handleChange}
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
