import { NextResponse } from 'next/server';
import { Props } from '@/app/interfaces/Props';
import { RequestCookie } from 'next/dist/compiled/@edge-runtime/cookies';
import { NextApiRequest, NextApiResponse } from 'next';

export async function validateJwt(token: string | undefined) {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/validate`, {
    method: 'GET',
    headers: {
      'content-type': 'application/json;charset=UTF-8',
      'Authentication': `Bearer ${token}`
    },
  });

  return res;
}

export default async function handler(req : NextApiRequest, res: NextApiResponse) {
  const token = req.cookies.token;
  const props = await validateJwt(token);
  return NextResponse.json(props);
}
