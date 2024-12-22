import { auth0 } from "@/lib/auth0";
import { NextRequest, NextResponse } from "next/server";

const GET = async (
  req: NextRequest,
  props: { params: Promise<{ id: string }> }
) => {
  const params = await props.params;
  const { token } = await auth0.getAccessToken();
  const id = params.id;
  const res = await fetch(`${process.env.API_URL}/todos/${id}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (!res.ok) {
    throw new Error("データが取得できませんでした。");
  }
  const data = await res.json();
  return NextResponse.json(data);
};

const DELETE = async (
  req: NextRequest,
  { params }: { params: Promise<{ id: string }> }
) => {
  const { token } = await auth0.getAccessToken();
  const id = (await params).id;
  const res = await fetch(`${process.env.API_URL}/todos/${id}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (!res.ok) {
    throw new Error("データが削除できませんでした。");
  }
  return NextResponse.json({});
};

const PUT = async (
  req: NextRequest,
  { params }: { params: Promise<{ id: string }> }
) => {
  const { token } = await auth0.getAccessToken();
  const id = (await params).id;
  const data = await req.json();
  const res = await fetch(`${process.env.API_URL}/todos/${id}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    throw new Error("データが更新できませんでした。");
  }
  return NextResponse.json({});
};

export { GET, DELETE, PUT };
