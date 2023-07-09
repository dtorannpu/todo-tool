import Header from "@/components/Header"
import { UserProvider } from "@auth0/nextjs-auth0/client";
import { withPageAuthRequired } from "@auth0/nextjs-auth0";


export default withPageAuthRequired(
    async function TodoLayout({ children }: any) {
        return (
            <UserProvider>
                <Header />
                <section>
                    {children}
                </section>
            </UserProvider>
        );
    }, { returnTo: '/todo' });