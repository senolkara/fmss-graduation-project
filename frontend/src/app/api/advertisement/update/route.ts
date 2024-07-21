import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'

export async function PUT(req: Request) {
  try {
    const { searchParams } = new URL(req.url);
    const id = searchParams.get('id');

    if(!id) return NextResponse.json({ success: true, message: "Advertisement ID is Required" });

    const formData = await req.json();
    const token = req.headers.get("Authorization")?.split(" ")[1];
    const res = await fetch(`http://localhost:8096/api/v1/advertisements/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(formData),
    });
    const data = await res.json();
    if (data.data) {
      return NextResponse.json({ success: true, message: "Advertisement Updated successfully!" });
    } else {
      return NextResponse.json({ success: false, message: "Failed to Update the Advertisement. Please try again!" });
    }
  } 
  catch (error) {
    console.log('Error in update a new advertisement :', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });

  }
}
