import React from "react";
import { render, fireEvent } from "@testing-library/react";
import { Props } from "@/app/interfaces/Props";
// import Register from "@/app/tiny-triumphs/register/page";

import Register from "../app/tiny-triumphs/register/page.tsx"

describe("<Register />", () => {
   

  test("should display a blank login form, with remember me checked by default", async () => {

     // Render the login form
     const { getByLabelText } = renderLoginForm();

     // Retrieve the form elements and assert their initial state
     const firstNameInput = getByLabelText("First Name") as HTMLInputElement;
     const lastNameInput = getByLabelText("Last Name") as HTMLInputElement;
     const emailInput = getByLabelText("Email") as HTMLInputElement;
     const passwordInput = getByLabelText("Password") as HTMLInputElement;
 
     expect(firstNameInput.value).toBe("");
     expect(lastNameInput.value).toBe("");
     expect(emailInput.value).toBe("");
     expect(passwordInput.value).toBe("");
   
  });
});


function renderLoginForm(props: Partial<Props> = {}) {
  const defaultProps: Props = {
    isAuthenticated: false,
    setIsAuthenticated(isAuthenticated) {
        return;
    },
    onFirstNameChange() {
      return;
    },
    onLastNameChange(){
      return;
    },
    onEmailChange(){
      return;
    },
    onPasswordChange(){
      return;
    },

    onSubmit() {
      return;
    }
  };
  return render(<Register {...props} {...defaultProps}/>);
}