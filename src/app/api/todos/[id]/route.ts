import { getAccessToken, withApiAuthRequired } from "@auth0/nextjs-auth0";
import { NextResponse } from "next/server";

const GET = withApiAuthRequired(async (req, ctx) => {
    const { accessToken } = await getAccessToken();
    const id = ctx.params?.id;
    const res = await fetch(`http://localhost:8080/todos/${id}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
    });

    if (!res.ok) {
        throw new Error('データが取得できませんでした。');
    }
    const data = await res.json();
    return NextResponse.json(data);
});

const DELETE = withApiAuthRequired(async (req, ctx) => {
    const { accessToken } = await getAccessToken();
    const id = ctx.params?.id;
    const res = await fetch(`http://localhost:8080/todos/${id}`, {
        method: 'DELETE',
        headers: {
            Authorization: `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
    });

    if (!res.ok) {
        throw new Error('データが削除できませんでした。');
    }
    return NextResponse.json({});
});

export { GET, DELETE };