import { NextRequest, NextResponse } from 'next/server';

export default async function register(registrationData: RegistrationData) {
  try {
    const res = await fetch('http://localhost:8000/api/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(registrationData), // Pass the parsed registrationData as JSON in the request body
    });
    const data = await res.json();

    return NextResponse.json({ data });
  } catch (error) {
    // Handle error
    console.error(error);
    return NextResponse.error();
  }
}
