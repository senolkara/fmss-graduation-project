
import { NextResponse } from "next/server";

export const dynamic  = 'force-dynamic'

export async function POST(req: Request) {
    try {
        const formData = await req.json();
        const token = req.headers.get("Authorization")?.split(" ")[1];
        const res = await fetch(`http://localhost:8097/api/v1/orders`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(formData),
        });
        return NextResponse.json({ success: true, message: "order saved!" });

    } catch (error) {
        console.log('error in register (service) => ', error);
        return NextResponse.json({ success: false, message: "Something Went Wrong Please Retry Later !" })
    }
}
