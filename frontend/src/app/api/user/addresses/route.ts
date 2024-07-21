import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function GET(req: Request) {
  try {

    const { searchParams } = new URL(req.url);
    const userId = searchParams.get('userId');

    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8090/api/v1/users/addressList/userId/${userId}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
    });
    const data = await res.json();
    return NextResponse.json({ data: data });


  } catch (error) {
    console.log('Error in getting addresses :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}

