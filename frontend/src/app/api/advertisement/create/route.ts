import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function POST(req: Request) {
  try {
    
    const formData = await req.json();
    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8096/api/v1/advertisements`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(formData),
    });
    return NextResponse.json({ success: true, message: "advertisement saved!" });
  } 
  catch (error) {
    console.log('Error in update a new advertisement :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });

  }
}
