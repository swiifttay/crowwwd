import {  useState, useEffect } from 'react';
import { useStripe } from "@stripe/react-stripe-js";
import { useRouter } from 'next/navigation'
import axios from 'axios';
import { BiLoaderAlt } from 'react-icons/bi';
import { Order } from '@/app/payment/[orderId]/page';
import { approvedFriend, confirmSeats, deleteOrderByOrderId, fetchOrderByPaymentId } from '@/app/axios/apiService';


export interface SeatsConfirmRequest { 
    orderId: string
    paymentId: string
    userIdsAttending: string[];
    noOfSurpriseTickets: number
}


const ProcessingPage = ({ clientSecret, paymentID }: any) => {
  const[message, setMessage] = useState("");
  const[order, setOrder] = useState<Order>();
  const [friendsList, setFriendsList] = useState<string[]>();


  const router = useRouter()
  
  const stripe = useStripe();

  const getFriendList = async () =>{
    const response = await approvedFriend();
    setFriendsList(response.data.friends);
    // console.log(response)
  }
  
  const confirmPayment = () => {
    getFriendList();

    const numFriends = friendsList?.length || 0
    console.log(numFriends)

    const numTickets = order?.seats.length || 0
    console.log(numTickets)

    const surpriseTickets = numTickets - numFriends
    console.log(surpriseTickets)

    console.log(paymentID)

    if (typeof order?.id === 'string' && order.id && Array.isArray(friendsList)) {
        try {
          const response = confirmSeats({
            orderId: order.id,
            paymentId: paymentID,
            userIdsAttending: friendsList,
            noOfSurpriseTickets: surpriseTickets,
          })
          console.log(response)
    } catch (error){
        console.log(error)
    }
    
  }
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
    if (stripe && clientSecret) { 
      fetchOrderByPaymentId(paymentID).then((response) => {setOrder(response.data.order)})
       console.log(order?.id) 

      stripe.retrievePaymentIntent(clientSecret).then(({ paymentIntent }) => {
        switch (paymentIntent.status) {
          case "succeeded":
            confirmPayment();
            router.push(`/order/${order?.id}`);
            setMessage("Payment success");
            console.log("Payment success");
            break;
          case "processing":
            setMessage("Your payment is processing.");
            console.log("Payment processing");
            break;
          default:
            cancelPayment();
            // router.push('/paymentunsuccessful');
            setMessage("Something went wrong.");
            console.log("Payment unsuccessful");
            break;
        }
      });
    }
  }, [stripe, clientSecret,order]); 

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
