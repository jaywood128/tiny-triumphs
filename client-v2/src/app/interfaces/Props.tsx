export interface Props {
  isAuthenticated: boolean;
  setIsAuthenticated: (isAuthenticated: boolean) => void;
  onFirstNameChange: (username: string) => void;
  onLastNameChange: (username: string) => void;
  onEmailChange: (username: string) => void;
  onPasswordChange: (username: string) => void;
  onSubmit: (usernamee: string, password: string) => void;
}