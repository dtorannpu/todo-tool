import { NextRequest } from "next/server";
import { auth0 } from "./lib/auth0";

export const middleware = async (request: NextRequest) => {
  return await auth0.middleware(request);
};

export const config = {
  matcher: ["/todo/:path*", "/api/todos/:path*"],
};
