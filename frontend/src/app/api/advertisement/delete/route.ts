import { NextResponse } from "next/server";

export const dynamic = 'force-dynamic'


export async function DELETE(req: Request) {
  try {

      const { searchParams } = new URL(req.url);
      const id = searchParams.get('id');

      if(!id) return NextResponse.json({ success: true, message: "Advertisement ID is Required" });

      const token = req.headers.get("Authorization")?.split(" ")[1];
      const res = await fetch(`http://localhost:8096/api/v1/advertisements/delete/${id}`, {
          method: 'DELETE',
          headers: {
              'Authorization': `Bearer ${token}`
          }
      });
      const data = await res.json();
      if (data.data) {
        return NextResponse.json({ success: true, message: "Advertisement Deleted successfully!" });
      } else {
        return NextResponse.json({ success: false, message: "Failed to Delete the Advertisement. Please try again!" });
      }
  } catch (error) {
    console.log('Error in deleting a new Advertisement:', error);
    return NextResponse.json({ success: false, message: 'Something went wrong. Please try again!' });
  }
}
