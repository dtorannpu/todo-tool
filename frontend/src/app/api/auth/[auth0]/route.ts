import { HandlerError, handleAuth, handleLogin } from "@auth0/nextjs-auth0";
import { NextRequest } from "next/server";

export const GET = handleAuth({
  login: handleLogin({ returnTo: "/todo" }),
  onError(req: NextRequest, error: HandlerError) {
    console.log(error);
  },
});
