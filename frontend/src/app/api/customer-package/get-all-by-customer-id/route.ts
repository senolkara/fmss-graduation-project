import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function GET(req: Request) {
  try {
    const { searchParams } = new URL(req.url);
    const customerId = searchParams.get('customerId');

    if(!customerId) return NextResponse.json({ success: true, message: "Customer ID is Required" });

    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8095/api/v1/packages/customerId/${customerId}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
    });
    const data = await res.json();
    if (data.data) {
      return NextResponse.json({ success: true, data: data.data });
    } else {
      return NextResponse.json({ success: false, message: 'Customer Packages Not Found' });
    }


  } catch (error) {
    console.log('Error in getting all Customer Packages data :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}
