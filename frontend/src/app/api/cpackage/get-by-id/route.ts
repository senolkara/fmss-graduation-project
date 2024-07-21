import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function GET(req: Request) {
  try {

    const { searchParams } = new URL(req.url);
    const cpackageId = searchParams.get('cpackageId');

    if(!cpackageId) return NextResponse.json({ success: true, message: "CPackage ID is Required" });

    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8095/api/v1/packages/id/${cpackageId}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
    });
    const data = await res.json();
    if (data.id) {
      return NextResponse.json({ success: true, data: data });
    } else {
      return NextResponse.json({ success: false, message: 'CPackage Not Found' });
    }


  } catch (error) {
    console.log('Error in getting customer :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}

