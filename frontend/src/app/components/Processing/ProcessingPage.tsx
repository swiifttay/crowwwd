import {  useState, useEffect } from 'react';
import { useStripe } from "@stripe/react-stripe-js";
import { useRouter } from 'next/navigation'
import axios from 'axios';
import { BiLoaderAlt } from 'react-icons/bi';
import { Order } from '@/app/payment/[orderId]/page';
import { confirmSeats, deleteOrderByOrderId, fetchOrderByPaymentId } from '@/app/axios/apiService';


export interface SeatsConfirmRequest { 
    orderId: string
    userIdsAttending: string[];
    noOfSurpriseTickets: number
}


const ProcessingPage = ({ clientSecret, paymentID }: any) => {
  const[message, setMessage] = useState("");
  const[order, setOrder] = useState<Order>();


  const router = useRouter()
  
  const stripe = useStripe();
    
  const get
  
  const confirmPayment = () => {
    const confirmParam: SeatsConfirmRequest = {
        orderId: order?.id,
        userIdsAttending: ["123", "456"],
        noOfSurpriseTickets: 1
    }
    confirmSeats(confirmParam);
  }


  const cancelPayment = async () => {
    try {
        await axios.post("/api/cancel-payment-intent", {
            paymentID
         });
        deleteOrderByOrderId(order?.id)
    } catch (error){
        console.log(error);
    }
    

  }
  

  useEffect(() => {
    fetchOrderByPaymentId(paymentID).then((response) => {setOrder(response.data)})
    

    if (stripe) { // Check if stripe is available
      stripe.retrievePaymentIntent(clientSecret).then(({ paymentIntent }) => {
        switch (paymentIntent.status) {
          case "succeeded":
            confirmPayment();
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
  }, [paymentID, stripe, clientSecret]); 

  return (
    <div>
      <div className="my-5 w-full flex text-4xl text-center font-thin justify-center items-center">
          <h1 className="px-3">Processing...</h1>
          <BiLoaderAlt className="animate-spin" />
        </div>
    </div>
  );
}

export default ProcessingPage;
