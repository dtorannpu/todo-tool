import { NextRequest, NextResponse } from "next/server";
import { auth0 } from "./lib/auth0";

export const middleware = async (request: NextRequest) => {
  const authRes = await auth0.middleware(request);
  if (request.nextUrl.pathname.startsWith("/auth")) {
    return authRes;
  }

  const session = await auth0.getSession();

  if (!session) {
    return NextResponse.redirect(`${process.env.APP_BASE_URL}/auth/login`);
  }

  return authRes;
};

export const config = {
  matcher: [
    /*
     * Match all request paths except for the ones starting with:
     * - _next/static (static files)
     * - _next/image (image optimization files)
     * - favicon.ico, sitemap.xml, robots.txt (metadata files)
     */
    "/((?!_next/static|_next/image|favicon.ico|sitemap.xml|robots.txt|$|/auth/.*).*)",
  ],
};
