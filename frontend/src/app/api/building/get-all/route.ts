import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function GET(req: Request) {
  try {
    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8096/api/v1/advertisements/allBuildings`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
    });
    const data = await res.json();
    if (data.data) {
      return NextResponse.json({ success: true, data: data.data });
    } else {
      return NextResponse.json({ success: false, message: 'Building Not Found' });
    }


  } catch (error) {
    console.log('Error in getting all Building data :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}
