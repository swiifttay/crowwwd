import { useState, useEffect } from 'react';
import { useStripe } from "@stripe/react-stripe-js";
import { useRouter } from 'next/navigation'

const ProcessingPage = ({ clientSecret }) => {
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");

  const router = useRouter()
  
  const stripe = useStripe();


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
      {/* Render your component content */}
      {loading ? <p>Loading...</p> : <p>{message}</p>}
    </div>
  );
}

export default ProcessingPage;
