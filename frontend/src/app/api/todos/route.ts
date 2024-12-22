import { auth0 } from "@/lib/auth0";
import { NextRequest, NextResponse } from "next/server";

const POST = async (req: NextRequest) => {
  const { token } = await auth0.getAccessToken();
  const data = await req.json();
  const res = await fetch(`${process.env.API_URL}/todos`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    throw new Error("登録に失敗しました。");
  }
  return NextResponse.json({});
};

export { POST };
