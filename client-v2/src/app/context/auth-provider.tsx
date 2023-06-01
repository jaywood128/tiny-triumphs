'use client';
 
import { createContext, useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import { ReactNode } from 'react';

interface AuthenticationContextProps {
  isAuthenticated: boolean;
  setIsAuthenticated: (value: boolean) => void;
}

export const AuthenticationContext = createContext<AuthenticationContextProps>({
  isAuthenticated: true,
  setIsAuthenticated: () => {},
});

interface AuthenticationProviderProps {
  children: ReactNode;
}

export default function AuthenticationProvider({ children }: AuthenticationProviderProps) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <AuthenticationContext.Provider value={{ isAuthenticated, setIsAuthenticated }}>
      {children}
    </AuthenticationContext.Provider>
  );
}

