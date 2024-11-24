import { getAccessToken } from "@auth0/nextjs-auth0";
import { NextRequest, NextResponse } from "next/server";

const POST = async (req: NextRequest) => {
  const { accessToken } = await getAccessToken();
  const data = await req.json();
  const res = await fetch(`${process.env.API_URL}/todos`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${accessToken}`,
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
