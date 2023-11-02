'use client'

import { useState, useEffect } from 'react'
import { useSearchParams } from 'next/navigation';
import {
    useStripe
  } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import ProcessingPage from '../components/Processing/ProcessingPage';



const orderprocessing = () => {

  const [loading, isLoading] = useState<Boolean>(true); 
  
  const stripePromise = loadStripe(
    process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY!
  );

  const searchParams = useSearchParams();
  const clientSecret: string | null = searchParams.get('payment_intent_client_secret') != null ? searchParams.get('payment_intent_client_secret') : null;
  

  const appearance = {
    theme: 'stripe',

  };
  const options:any = {
    clientSecret,
    appearance,
  };


  return (
    <div>
        {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
          <ProcessingPage clientSecret ={clientSecret} />
        </Elements>
      )}
    </div>
  );
}

export default orderprocessing;






