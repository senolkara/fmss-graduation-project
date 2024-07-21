import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function GET(req: Request) {
  try {

    const { searchParams } = new URL(req.url);
    const userId = searchParams.get('userId');

    if(!userId) return NextResponse.json({ success: true, message: "User ID is Required" });

    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8091/api/v1/customers/userId/${userId}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
    });
    const data = await res.json();
    if (data.userId) {
      return NextResponse.json({ success: true, data: data });
    } else {
      return NextResponse.json({ success: false, message: 'Customer Not Found' });
    }


  } catch (error) {
    console.log('Error in getting customer :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}

