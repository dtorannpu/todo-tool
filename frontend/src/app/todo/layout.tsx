import Header from "@/components/Header";
import { UserProvider } from "@auth0/nextjs-auth0/client";

export default function TodoLayout({ children }: any) {
  return (
    <UserProvider>
      <Header />
      <section>{children}</section>
    </UserProvider>
  );
}
