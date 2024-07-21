import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function GET(req: Request) {
  try {
    const { searchParams } = new URL(req.url);
    const id = searchParams.get('id');

    if(!id) return NextResponse.json({ success: true, message: "ID is Required" });

    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8096/api/v1/advertisements/id/${id}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
    });
    const data = await res.json();
    if (data.id) {
      return NextResponse.json({ success: true, data: data });
    } else {
      return NextResponse.json({ success: false, message: 'Advertisement Not Found' });
    }


  } catch (error) {
    console.log('Error in getting Advertisement data :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}
