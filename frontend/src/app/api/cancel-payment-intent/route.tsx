import { NextResponse, NextRequest } from "next/server";
import Stripe from "stripe";

const stripe = new Stripe(process.env.STRIPE_SECRET_KEY!, {
  typescript: true,
  apiVersion: "2023-08-16",
});



export async function POST(req: NextRequest) {
  try {
    // Parse the JSON request body to get the clientSecret
    const { paymentID } = await req.json();

    // Use the Stripe API to cancel the payment intent
    const paymentIntent = await stripe.paymentIntents.cancel(paymentID);

    // Return a successful response with the canceled payment intent
    return new NextResponse(paymentIntent, { status: 200 });
  } catch (error) {
    // Handle any errors that occur during the cancellation process
    console.error('Error canceling payment intent:', error);

    // Return an error response with a 400 status code
    return new NextResponse({ error: 'Payment intent cancellation failed' }, {
      status: 400,
    });
  }
}




