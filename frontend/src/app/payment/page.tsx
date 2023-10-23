'use client'

import {useState, useEffect} from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

import CheckoutForm from "../Payments/CheckoutForm";
import createPaymentIntent from "../axios/apiService";
import axios from "axios";

// Make sure to call loadStripe outside of a component’s render to avoid
// recreating the Stripe object on every render.
// This is your test publishable API key.
const stripePromise = loadStripe(
  process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY!
);

export default function App() {
  const [clientSecret, setClientSecret] = useState("");

  useEffect(() => {
    const paymentIntent = async () => {
      const { data } = await axios.post("/api/create-payment-intent", {
        data: { amount: 89 },
      });
        setClientSecret(data);
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
    <div className="w-full h-full p-5 flex space-x-10">
      <div className="w-1/2 mb-10 rounded-3xl bg-checkout bg-cover bg-center flex flex-col justify-center items-center shadow-lg shadow-slate-950">
        <h1 className="m-2 text-5xl font-semibold text-center">
          You're on your way!
        </h1>
        <h3></h3>
      </div>
      <div className="w-1/2">
      {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
          <CheckoutForm />
        </Elements>
      )}
      </div>
      
    </div>
  );
}