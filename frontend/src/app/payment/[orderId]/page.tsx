'use client'

import {useState, useEffect} from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

import CheckoutForm from "../../Payments/CheckoutForm";
import axios from "axios";
import { fetchOrderByOrderId } from "@/app/axios/apiService";


export interface Order {
  id: string
  paymentId: string
  seats: string[]
  payingUserId: string
  eventId: string
  category: string
  eventDate: string
  totalCost: number
  pricePerSeat: number
  
}

// Make sure to call loadStripe outside of a component’s render to avoid
// recreating the Stripe object on every render.
// This is your test publishable API key.
const stripePromise = loadStripe(
  process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY!
);

export default function App({ params }: { params: { orderId: string } }) {
  const { orderId } = params;

  const [clientSecret, setClientSecret] = useState("");
  const [order, setOrder] = useState<Order>();
  const [totalCost, setTotalCost] = useState<Number>();
  

  useEffect(() => {
    // fetchOrderByOrderId(orderId).then((response)=> {setOrder(response.data)})

    setTotalCost(100); // change to order?.totalCost


    const paymentIntent = async () => {
      try{
        const { data } = await axios.post("/api/create-payment-intent", {
          data: { amount: totalCost },
        });
          setClientSecret(data);
          console.log(clientSecret)
      } catch (error){
        console.log(error)
      }
    }
    paymentIntent();
    //add in api call to store paymentId

  }, [totalCost]);

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
          {`Pay $${totalCost}`}
        </h1>
        <br />
        <h2 className="m-2 text-2xl font-semibold text-center">You're on your way</h2>
      </div>
      <div className="w-1/2">
      {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
          <CheckoutForm clientSecret={clientSecret} totalCost={totalCost}/>
        </Elements>
      )}
      </div>
      
    </div>
  );
}