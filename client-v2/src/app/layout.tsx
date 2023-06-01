"use client";
import './globals.css';
import { Inter } from 'next/font/google';
import Link from 'next/link';
import AuthenticationProvider from './context/auth-provider';
import { useContext, useEffect, useState } from 'react';
import { AuthenticationContext } from "./context/auth-provider";
import { redirect } from 'next/navigation';
import { log } from 'console';

const inter = Inter({ subsets: ['latin'] });   








export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {

  // const {isAuthenticated, setIsAuthenticated} = useContext(AuthenticationContext)
  const { isAuthenticated, setIsAuthenticated } = useContext(AuthenticationContext);
  const [token, setToken] = useState<string | null>(null);

  let countWithDep = 0;


const handleClick = () => {
  logout();
  
}
  async function logout() {
  
    try {
      console.log(`validateJwt token: ${window.localStorage.getItem('token')}`)
      if (token === ""){
        return;
      }
      const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/logout`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${window.localStorage.getItem('token')}`
        },
      });
      console.log(res)
      if (res.ok) {
        console.log("Logout success")
        setIsAuthenticated(false);
        window.localStorage.removeItem('token')
        window.location.replace('/');
      }
    } catch (error) {
      console.log(error);
    }
  }

  

  useEffect(()=> {

    async function validateJwt() {
  
      try {
        console.log(`validateJwt token: ${window.localStorage.getItem('token')}`)
        if (token === ""){
          return;
        }
        const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/validate`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${window.localStorage.getItem('token')}`
          },
        });
        console.log(res)
        if (res.ok) {
          console.log("Setting isAuthenticated to true!")
          setIsAuthenticated(true);
          if (window.location.pathname !== "/tiny-triumphs") {
            window.location.replace('/tiny-triumphs');
          }
         
        } else {
          setIsAuthenticated(false);
        }
      } catch (error) {
        console.log(error);
      }
    }
    console.log(isAuthenticated)

    if (window.localStorage.getItem('token')){
      console.log("Calling validateJwt")
      validateJwt()
    }

  }, [isAuthenticated, setIsAuthenticated])

  return (
    <AuthenticationProvider>
    <html lang='en'>
      {/* <body className="bg-[#101820FF] h-16 w-16 flex justify-center items-center">{children}</body> */}
      <body>
        <div className='flex flex-col h-full bg-grey-900'>
          <nav className='bg-gray-800'>
            <div className='mx-auto max-w-7xl px-2 sm:px-6 lg:px-8'>
              <div className='relative flex h-16 items-center justify-between'>
                <div className='absolute inset-y-0 left-0 flex items-center sm:hidden'>
                  {/* <!-- Mobile menu button--> */}
                  <button
                    type='button'
                    className='inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white'
                    aria-controls='mobile-menu'
                    aria-expanded='false'
                  >
                    <span className='sr-only'>Open main menu</span>
                    {/* <!--
      Icon when menu is closed.

      Menu open: "hidden", Menu closed: "block"
    --> */}
                    <svg
                      className='block h-6 w-6'
                      fill='none'
                      viewBox='0 0 24 24'
                      strokeWidth='1.5'
                      stroke='currentColor'
                      aria-hidden='true'
                    >
                      <path
                        strokeLinecap='round'
                        strokeLinejoin='round'
                        d='M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5'
                      />
                    </svg>
                    {/* <!--
      Icon when menu is open.

      Menu open: "block", Menu closed: "hidden"
    --> */}
                    <svg
                      className='hidden h-6 w-6'
                      fill='none'
                      viewBox='0 0 24 24'
                      strokeWidth='1.5'
                      stroke='currentColor'
                      aria-hidden='true'
                    >
                      <path
                        strokeLinecap='round'
                        strokeLinejoin='round'
                        d='M6 18L18 6M6 6l12 12'
                      />
                    </svg>
                  </button>
                </div>
                <div className='flex flex-1 items-center justify-center sm:items-stretch sm:justify-start'>
                  <div className='flex flex-shrink-0 items-center'>
                    <img
                      className='block h-8 w-auto lg:hidden'
                      src='https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=500'
                      alt='Your Company'
                    />
                    <img
                      className='hidden h-8 w-auto lg:block'
                      src='https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=500'
                      alt='Your Company'
                    />
                  </div>
                  <div className='hidden sm:ml-6 sm:block'>
                    <div className='flex space-x-4'>
                      {/* <!-- Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" --> */}
                      <Link
                        href='/users/[id]/todos'
                        as={`/users/1/todos`}
                        className='bg-gray-900 text-white rounded-md px-3 py-2 text-sm font-medium'
                        aria-current='page'
                      >
                        Todo Items
                      </Link>
                      <a
                        href='#'
                        className='text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium'
                      >
                        Projects
                      </a>
                    </div>
                  </div>
                </div>
                <div className='absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0'>
                  <button
                    type='button'
                    className='rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800'
                  >
                    <span className='sr-only'>View notifications</span>
                    <svg
                      className='h-6 w-6'
                      fill='none'
                      viewBox='0 0 24 24'
                      strokeWidth='1.5'
                      stroke='currentColor'
                      aria-hidden='true'
                    >
                      <path
                        strokeLinecap='round'
                        strokeLinejoin='round'
                        d='M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0'
                      />
                    </svg>
                  </button>

                  {/* <!-- Profile dropdown --> */}
                  <div className='relative ml-3'>
                    <div>
                      <button  
                        type='button'
                        className='flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800'
                        id='user-menu-button'
                        aria-expanded='false'
                        aria-haspopup='true'
                      >
                        <span className='sr-only'>Open user menu</span>
                        <img
                          className='h-8 w-8 rounded-full'
                          src='https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                          alt=''
                        />
                      </button>
                    </div>

                    {/* <!--
      Dropdown menu, show/hide based on menu state.

      Entering: "transition ease-out duration-100"
        From: "transform opacity-0 scale-95"
        To: "transform opacity-100 scale-100"
      Leaving: "transition ease-in duration-75"
        From: "transform opacity-100 scale-100"
        To: "transform opacity-0 scale-95"
    --> */}
                    <div
                      className='absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none'
                      role='menu'
                      aria-orientation='vertical'
                      aria-labelledby='user-menu-button'
                      tabIndex={-1}
                    >
                      {/* <!-- Active: "bg-gray-100", Not Active: "" --> */}
                      <Link
                        href='/users/profile'
                        className='block px-4 py-2 text-sm text-gray-700'
                      >
                        Your profile
                      </Link>
                      {/* <a
              href='#'
              className='block px-4 py-2 text-sm text-gray-700'
              role='menuitem'
              tabIndex={-1}
              id='user-menu-item-0'
            >
              Your Profile
            </a> */}
                      <a
                        href='#'
                        className='block px-4 py-2 text-sm text-gray-700'
                        role='menuitem'
                        tabIndex={-1}
                        id='user-menu-item-1'
                      >
                        Settings
                      </a>
                      {isAuthenticated ? (
                        <button
                          onClick={handleClick}
                          className="block px-4 py-2 text-sm text-gray-700"
                          role="menuitem"
                          tabIndex={-1}
                          id="user-menu-item-2"
                        >
                          Sign out
                        </button>
                      ) : null}   
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* <!-- Mobile menu, show/hide based on menu state. --> */}
            <div className='sm:hidden' id='mobile-menu'>
              <div className='space-y-1 px-2 pb-3 pt-2'>
                {/* <!-- Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" --> */}
                <a
                  href='#'
                  className='bg-gray-900 text-white block rounded-md px-3 py-2 text-base font-medium'
                  aria-current='page'
                >
                  Dashboard
                </a>
                <a
                  href='#'
                  className='text-gray-300 hover:bg-gray-700 hover:text-white block rounded-md px-3 py-2 text-base font-medium'
                >
                  Team
                </a>
                <a
                  href='#'
                  className='text-gray-300 hover:bg-gray-700 hover:text-white block rounded-md px-3 py-2 text-base font-medium'
                >
                  Projects
                </a>
                <a
                  href='#'
                  className='text-gray-300 hover:bg-gray-700 hover:text-white block rounded-md px-3 py-2 text-base font-medium'
                >
                  Calendar
                </a>
              </div>
            </div>
          </nav>
          <div className="flex justify-center bg-gray-900 text-white text-2xl">
          <header>
          <h1>Tiny Triumphs Todo list 2023</h1>
        </header>
          </div>
         
        </div>
       
        {children}
       
       
      </body>
    </html>
    </AuthenticationProvider>
  );
}
