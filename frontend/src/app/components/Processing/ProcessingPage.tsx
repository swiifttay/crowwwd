import {  useState, useEffect } from 'react';
import { useStripe } from "@stripe/react-stripe-js";
import { useRouter } from 'next/navigation'
import axios from 'axios';
import { BiLoaderAlt } from 'react-icons/bi';

const ProcessingPage = ({ clientSecret, paymentID }) => {
  const [message, setMessage] = useState("");

  const router = useRouter()
  
  const stripe = useStripe();

  const secretKey = process.env.STRIPE_SECRET_KEY;

  const cancelPayment = async () => {
    await axios.post("/api/cancel-payment-intent", {
       paymentID
    });
  }

  useEffect(() => {
    if (stripe) { // Check if stripe is available
      stripe.retrievePaymentIntent(clientSecret).then(({ paymentIntent }) => {
        switch (paymentIntent.status) {
          case "succeeded":
            router.push("/order");
            setMessage("Payment success");
            console.log("Payment success");
            break;
          case "processing":
            setMessage("Your payment is processing.");
            console.log("Payment processing");
            break;
          default:
            cancelPayment();
            router.push('/paymentunsuccessful');
            setMessage("Something went wrong.");
            console.log("Payment unsuccessful");
            break;
        }
      });
    }
  }, [stripe, clientSecret]); 

  return (
    <div>
      <div className="my-5 w-full flex text-4xl text-center font-thin justify-center">
          <h1 className="px-3">Processing...</h1>{" "}
          <BiLoaderAlt className="animate-spin" />
        </div>
    </div>
  );
}

export default ProcessingPage;
