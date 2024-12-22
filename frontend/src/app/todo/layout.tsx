import Header from "@/components/Header";
import { Auth0Provider } from "@auth0/nextjs-auth0";

export default function TodoLayout({ children }: any) {
  return (
    <Auth0Provider>
      <Header />
      <section>{children}</section>
    </Auth0Provider>
  );
}
