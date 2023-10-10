'use client'

import {useState, useEffect} from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

import CheckoutForm from "../Payments/CheckoutForm";
import createPaymentIntent from "../axios/apiService";

// Make sure to call loadStripe outside of a component’s render to avoid
// recreating the Stripe object on every render.
// This is your test publishable API key.
const stripePromise = loadStripe('pk_test_51NxKRHKEafGwrR3ZJnpGsVXU7gh8DaQszUOQebCNiMoYnL8OsSsMV6BXTY4WyxuX5FFRvRPm4qqVrYiQUCFew75M00iEBL7DFs');

export default function App() {
  const [clientSecret, setClientSecret] = useState("");

  useEffect(() => {
    const paymentIntent = async () => {
        const response:any = await createPaymentIntent({body: "ticket"});
        setClientSecret(response);
        // console.log(response)
    }
    paymentIntent();
  }, []);

  const appearance = {
    theme: 'stripe',
  };
  const options:any = {
    clientSecret,
    appearance,
  };

  return (
    <div className="w-full p-5 flex justify-end">
      {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
          <CheckoutForm />
        </Elements>
      )}
    </div>
  );
}