import { useEffect, useState } from "react";
import {
  PaymentElement,
  LinkAuthenticationElement,
  useStripe,
  useElements
} from "@stripe/react-stripe-js";
import axios from 'axios';
import { useRouter } from "next/navigation";
import PaymentTimeout from "../components/Processing/PaymentTimeout"

export default function CheckoutForm({clientSecret}:any) {
  const stripe = useStripe();
  const elements = useElements();
  const router = useRouter();

  const [email, setEmail] = useState<string>('');
  const [message, setMessage] = useState<string|undefined>('');
  const [isLoading, setIsLoading] = useState(false);
  const [paymentID, setPaymentID] = useState<string|undefined>("");
  let timeoutId: NodeJS.Timeout | null = null;
  

  const retrievePaymentID = async (clientSecret:any) =>{
    if (stripe){
      const response = await stripe.retrievePaymentIntent(clientSecret)
      const id = response.paymentIntent?.id
      setPaymentID(id);
    }
  }

  useEffect(() => {
    console.log(clientSecret)
    retrievePaymentID(clientSecret);

    timeoutId = setTimeout(async () => {
      if (!stripe || !clientSecret) {
        return;
      }
    
    
      try {
        await axios.post("/api/cancel-payment-intent", {
          paymentID: paymentID
       });
       router.push('/timeout');
      } catch (error) {
        console.error("Error cancelling payment:", error);
      }
    }, 120000); 

    return () => {
      // Cleanup function to clear the timeout when the component unmounts
      if (timeoutId) {
        clearTimeout(timeoutId);
      }
    };

  }, [stripe, clientSecret, paymentID]);

  const handleSubmit:any = async (e: React.FormEvent<HTMLFormElement>) => 
  {
    e.preventDefault();

    if (!stripe || !elements) {
      // Stripe.js hasn't yet loaded.
      // Make sure to disable form submission until Stripe.js has loaded.
      return;
    }

    setIsLoading(true);


    const { error } = await stripe.confirmPayment({
      elements,
      confirmParams: {
        // Make sure to change this to your payment completion page
        return_url: `${window.location.origin}/orderprocessing`,
        
      },
    });

    // This point will only be reached if there is an immediate error when
    // confirming the payment. Otherwise, your customer will be redirected to
    // your `return_url`. For some payment methods like iDEAL, your customer will
    // be redirected to an intermediate site first to authorize the payment, then
    // redirected to the `return_url`.
    if (error.type === "card_error" || error.type === "validation_error") {
      setMessage(error.message);
    } else {
      setMessage("An unexpected error occurred.");
    }
    

    setIsLoading(false);
  };

  const paymentElementOptions: any = {
    layout: "tabs",
  };

  return (
    <form id="payment-form" onSubmit={handleSubmit}>
      <LinkAuthenticationElement
        id="link-authentication-element"
        onChange={(e) => setEmail(e.target.value)}
      />
      <PaymentElement id="payment-element" options={paymentElementOptions} />
      <button disabled={isLoading || !stripe || !elements} id="submit" className="rounded-md my-5 p-5 bg-white text-black">
        <span id="button-text">
          {isLoading ? <div className="spinner" id="spinner"></div> : "Pay now"}
        </span>
      </button>
      {/* Show any error or success messages */}
      {message && <div id="payment-message">{message}</div>}
    </form>
   );
}