

import { useFormState } from "../components/Register/FormContext";
import { SimpleDetailForm } from "../components/Register/SimpleDetailForm";
import { ComplexDetailForm } from "../components/Register/ComplexDetailForm";

export function ActiveStepFormComponent() {
  const { step } = useFormState();
  switch (step) {
    case 0:
      // return <SimpleDetailForm />;
      return <SimpleDetailForm />
    case 1:
      return <ComplexDetailForm />;
    // case 3:
    //   return <PasswordForm />;
    default:
      return null;
  }
}