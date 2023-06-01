"use client";
import { GetServerSideProps } from "next";
import { Props } from "./interfaces/Props";
import { RequestCookie } from 'next/dist/compiled/@edge-runtime/cookies';
import { NextApiRequest, NextApiResponse } from 'next';
import { NextResponse } from 'next/server';
import Link from 'next/link';
import { useEffect, useState } from "react";
import { useContext } from 'react';
import { AuthenticationContext } from "./context/auth-provider";
import { redirect } from "next/navigation";


  export default function welcomePage (props : Props ) {

    const {isAuthenticated, setIsAuthenticated} = useContext(AuthenticationContext)

    // useEffect(() => {
    //   if (isAuthenticated) {
    //     redirect('/tiny-triumphs');
    //   } else {
    //    redirect('/');
    //   }
    // }, [isAuthenticated]);

   

    return (
    
       <div>
        <h1>Welcome to Tiny Triumphs todolist application. You {isAuthenticated? "are" : "are not"} logged in. Please login or register.</h1>
        <Link href="/tiny-triumphs/login">Login</Link> <br/>
        <Link href="/tiny-triumphs/register">Register</Link>
      </div>

    );
  };