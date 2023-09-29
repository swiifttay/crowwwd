import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from "react";

interface IFormContext {
  formData: any;
  setFormData: Dispatch<SetStateAction<any>>;
  onHandleNext: () => void;
  onHandleBack: () => void;
  step: number;
}

const FormContext = createContext<IFormContext>({
  formData: {},
  onHandleNext: () => {},
  onHandleBack: () => {},
  setFormData: () => {},
  step: 0,
});

interface IProps {
  children: ReactNode;
}

export function FormProvider({ children }: IProps) {
  const [formData, setFormData] = useState();
  const [step, setStep] = useState(0);

  function onHandleNext() {
    setStep(() => 1);
  }

  function onHandleBack() {
    setStep(() => 0);
  }

  return (
    <FormContext.Provider
      value={{ formData, setFormData, onHandleNext, onHandleBack, step }}
    >
      {children}
    </FormContext.Provider>
  );
}

export function useFormState() {
  return useContext(FormContext);
}

// export function useFormState() {

//   console.log("using");
//   const [formData, setFormData] = useState();
//   const [step, setStep] = useState(1);

//   function onHandleNext() {
//     console.log("arrived");
//     setStep((prev) => prev + 1);
//   }

//   return (
//     <FormContext.Provider
//       value={{ formData, setFormData, onHandleNext, step }}
//     >
//       {children}
//     </FormContext.Provider>
//   );
// }
